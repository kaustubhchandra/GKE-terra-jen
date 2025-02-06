provider "google" {
  project = "asysops"
  region  = "asia-south1"
}

resource "google_container_cluster" "primary" {
  name     = "highway-cluster"
  location = "asia-south1-b"

  initial_node_count = 2
  node_config {
    machine_type = "e2-medium"
  }

  enable_autoscaling {
    min_node_count = 2
    max_node_count = 5
  }

output "cluster_endpoint" {
  value = google_container_cluster.primary.endpoint
}


