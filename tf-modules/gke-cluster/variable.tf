variable "project" {
  description = "The Google Cloud project to use"
  type        = string
<<<<<<< HEAD
  description = "The Google Cloud Project ID"
  default     = "a-sysops"
=======
  default     = "asysops"
>>>>>>> 7e1f5c3cc94a0fab1856f6b6ed3abedc58c0cd65
}

variable "region" {
  description = "The region for the GKE cluster"
  type        = string
<<<<<<< HEAD
  description = "The Google Cloud region"
  default     = "us-west1"
=======
  default     = "asia-south1"
}

variable "zone" {
  description = "The zone to create the GKE cluster"
  type        = string
  default     = "asia-south1-b"
}

variable "machine_type" {
  description = "Machine type for the GKE nodes"
  type        = string
  default     = "e2-medium"
}

variable "min_node_count" {
  description = "Minimum node count for the GKE cluster autoscaler"
  type        = number
  default     = 2
}

variable "max_node_count" {
  description = "Maximum node count for the GKE cluster autoscaler"
  type        = number
  default     = 5
>>>>>>> 7e1f5c3cc94a0fab1856f6b6ed3abedc58c0cd65
}
