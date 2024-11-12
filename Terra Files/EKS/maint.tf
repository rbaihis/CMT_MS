# main.tf

provider "aws" {
  region  = "eu-west-2"
  profile = "default"
}

module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "~> 20.0"

  cluster_name    = "my-first-terra-cluster"
  cluster_version = "1.30"

  cluster_endpoint_public_access  = true

  cluster_addons = {
    coredns                = {}
    eks-pod-identity-agent = {}
    kube-proxy             = {}
    vpc-cni                = {}
  }

  vpc_id                   = "vpc-0f64564a2d8b6e31f"
  subnet_ids               = ["subnet-0147b4b40b0cc8056", "subnet-010294b7e8cf14145", "subnet-0c98ea8d660310c91"]

  # Associate the IAM role and security group
  eks_managed_node_group_defaults = {
    instance_types = ["t3.medium"]
    iam_role_arn   = aws_iam_role.eks_node_role.arn
  }

  eks_managed_node_groups = {
    example = {
      ami_type       = "AL2023_x86_64_STANDARD"
      instance_types = ["t3.medium"]

      min_size     = 2
      max_size     = 3
      desired_size = 2

      # Security group for the worker nodes
    #  additional_security_group_ids = [aws_security_group.eks-security-group]
      vpc_security_group_ids = [aws_security_group.eks_security_group.id]
    }
  }

  enable_cluster_creator_admin_permissions = true

  access_entries = {
    example = {
      kubernetes_groups = []
      principal_arn     = aws_iam_role.eks_node_role.arn
        #aws_iam_role.eks_node_role.arn

      policy_associations = {
        example = {
          policy_arn = "arn:aws:eks::aws:cluster-access-policy/AmazonEKSViewPolicy"
          access_scope = {
            namespaces = ["default"]
            type       = "namespace"
          }
        }
      }
    }
  }

  tags = {
    Environment = "dev"
    Terraform   = "true"
  }
}
