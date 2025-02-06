provider "google" {
  project = var.project
  region  = var.region
}

resource "google_container_cluster" "primary" {
  name     = "highway-cluster"
  location = var.zone

  initial_node_count = 2

  node_config {
    machine_type = var.machine_type
  }

  enable_autoscaling {
    min_node_count = var.min_node_count
    max_node_count = var.max_node_count
  }


resource "google_container_node_pool" "primary_nodes" {
  name       = "primary-node-pool"
  cluster    = google_container_cluster.primary.name
  location   = var.zone
  node_count = 2

  node_config {
    machine_type = var.machine_type
  }

  autoscaling {
    min_node_count = var.min_node_count
    max_node_count = var.max_node_count
  }
}
