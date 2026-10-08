<<<<<<< HEAD
resource "aws_s3_bucket" "devops553" {
=======
resource "aws_s3_bucket" "neeshu24619" {
>>>>>>> 28f2306 (commit)
  bucket        = var.bucket_name
  force_destroy = true
  tags = {
    Name        = var.bucket_name
    Environment = "dev"
    ManagedBy   = "Terraform"
    Project     = "Session18"
  }
}
