# Configure AWS provider with proper credentials
provider "aws" {
  region  = "eu-west-2"
  profile = "myuser"
}

# Create default VPC if one does not exist
resource "aws_default_vpc" "default_vpc" {
  tags = {
    Name = "default vpc"
  }
}

# Use data source to get all availability zones in the region
data "aws_availability_zones" "available_zones" {}

# Create a default subnet in the first availability zone if one does not exist
resource "aws_default_subnet" "subnet_az1" {
  availability_zone = data.aws_availability_zones.available_zones.names[0]
}

# Create a default subnet in the second availability zone if one does not exist
resource "aws_default_subnet" "subnet_az2" {
  availability_zone = data.aws_availability_zones.available_zones.names[1]
}

# Create security group for the web server
resource "aws_security_group" "webserver_security_group" {
  name        = "webserver_security_group"
  description = "Enable HTTP access on port 80"
  vpc_id      = aws_default_vpc.default_vpc.id

  ingress {
    description = "HTTP access"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "webserver_security_group"
  }
}

# Create security group for the database
resource "aws_security_group" "database_security_group" {
  name        = "database_security_group"
  description = "Enable PostgreSQL access on port 5432"
  vpc_id      = aws_default_vpc.default_vpc.id

  ingress {
    description     = "PostgreSQL access"
    from_port       = 5432
    to_port         = 5432
    protocol        = "tcp"
    security_groups = [aws_security_group.webserver_security_group.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "database_security_group"
  }
}

# Create the subnet group for the RDS instance
resource "aws_db_subnet_group" "database_subnet_group" {
  name        = "db_subnet_group"
  subnet_ids  = [aws_default_subnet.subnet_az1.id, aws_default_subnet.subnet_az2.id]
  description = "Database subnet group"

  tags = {
    Name = "db_subnet_group"
  }
}

# Create the RDS instance
resource "aws_db_instance" "db_instance" {
  engine                  = "postgres"
  engine_version          = "13.4"
  multi_az                = false
  identifier              = "cmt-solution-db"
  username                = "cmt-solution"
  password                = "cmt-solution"
  instance_class          = "db.t4g.micro"
  allocated_storage       = 20
  db_subnet_group_name    = aws_db_subnet_group.database_subnet_group.name
  vpc_security_group_ids  = [aws_security_group.database_security_group.id]
  availability_zone       = "eu-west-2a"
  db_name                 = "cmt_solution_db"
  skip_final_snapshot     = true
}
