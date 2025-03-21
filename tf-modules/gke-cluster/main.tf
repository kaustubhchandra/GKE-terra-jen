provider "google" {
<<<<<<< HEAD
  credentials = file("gke-sa.json")  # Path to your service account key
  project     = "a-sysops"  # Replace with your Google Cloud project ID
  region      = "us-west1"  # You can change the region to your preference
}

resource "google_container_cluster" "primary" {
  name     = "my-gke-cluster"  # Name of your GKE cluster
  location = "us-west1-a"  # Change to your desired zone

  initial_node_count = 2  # Number of nodes in your cluster

  master_auth {
    client_certificate_config {
      issue_client_certificate = true  # This enables client certificates for master authentication
    }
  }

  node_config {
    machine_type = "e2-medium"  # Node machine type (you can change this)
  }
}


=======
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
>>>>>>> 7e1f5c3cc94a0fab1856f6b6ed3abedc58c0cd65
