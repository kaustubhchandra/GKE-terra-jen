provider "google" {
  project = "your-project-id"
  region  = "us-central1"
}

resource "google_container_cluster" "primary" {
  name     = "highway-cluster"
  location = "us-central1-a"

  initial_node_count = 2
  node_config {
    machine_type = "e2-medium"
  }

  enable_autoscaling {
    min_node_count = 2
    max_node_count = 5
  }

  master_auth {
    username = "admin"
    password = "password"
  }
}

output "cluster_name" {
  value = google_container_cluster.primary.name
}

output "cluster_endpoint" {
  value = google_container_cluster.primary.endpoint
}

output "cluster_ca_certificate" {
  value = google_container_cluster.primary.master_auth[0].cluster_ca_certificate
}
