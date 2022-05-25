// SPDX-License-Identifier: GPL-3.0-or-later WITH GPL-3.0-linking-source-exception
// SPDX-FileCopyrightText: 2019-2020 Normation SAS

use std::{path::PathBuf, sync::Arc};

use reqwest::Body;
use tracing::{debug, instrument};

use crate::{configuration::Secret, processing::inventory::InventoryType, Error, JobConfig};

#[instrument(name = "upstream", level = "debug", skip(job_config))]
pub async fn send_report(job_config: Arc<JobConfig>, path: PathBuf) -> Result<(), Error> {
    forward_file(
        job_config.clone(),
        "reports",
        path,
        job_config.cfg.output.upstream.password.clone(),
    )
    .await
}

#[instrument(name = "upstream", level = "debug", skip(job_config))]
pub async fn send_inventory(
    job_config: Arc<JobConfig>,
    path: PathBuf,
    inventory_type: InventoryType,
) -> Result<(), Error> {
    forward_file(
        job_config.clone(),
        match inventory_type {
            InventoryType::New => "inventories",
            InventoryType::Update => "inventory-updates",
        },
        path,
        match inventory_type {
            InventoryType::New => job_config.cfg.output.upstream.default_password.clone(),
            InventoryType::Update => job_config.cfg.output.upstream.password.clone(),
        },
    )
    .await
}

async fn forward_file(
    job_config: Arc<JobConfig>,
    endpoint: &str,
    path: PathBuf,
    password: Secret,
) -> Result<(), Error> {
    let content = tokio::fs::read(path.clone()).await?;

    let client = job_config.upstream_client.read().await.inner().clone();

    let result = client
        .put(&format!(
            "{}/{}/{}",
            job_config.cfg.upstream_url(),
            endpoint,
            path.file_name().expect("not a file").to_string_lossy()
        ))
        .basic_auth(
            &job_config.cfg.output.upstream.user,
            Some(&password.value()),
        )
        .body(Body::from(content))
        .send()
        .await;

    result
        // HTTP error -> Err()
        .and_then(|r| r.error_for_status())
        .map(|r| debug!("Server response: {:#?}", r))?;
    Ok(())
}
