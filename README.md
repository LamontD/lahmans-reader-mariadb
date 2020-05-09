# lahmans-reader-mariadb
## Short Description
A SpringBoot reader of the Lahman's Baseball DB (stored in MariaDB), with a few other little things for my own purposes.

## Say What?
This is part of some infrastructure that I'm using to test out a polyglot storage pattern that functions as follows:

- A gold copy of the data is stored in a RDBMS
- Other derivative data stores exist with different representations of the data
- Updates to the RDBMS are published by a messaging architecture
- Derivative data stores read these messages and update their data stores.

This is an eventually consistent architecture, but it works -- or at least it _should_ work. But I needed a dataset that 
I could use to try and tease out the appropriate functions.

## Original Data: The Lahman Baseball Database
I found the Lahman Baseball Database available online (http://www.seanlahman.com/baseball-archive/statistics/), and it 
seemed like a good fit: There's a ton of data available, it has a relational model that already exists, and it would be
pretty easy to get started.

The database is an awesome resource, and it contains downloadable versions that are ready to load in your database of choice. 
Please note that it is licensed under a Creative Commons Attribution-ShareAlike Unported 3.0 license (CC BY-SA 3.0), and 
I'm making this software available under the same license. You can find those license details here: https://creativecommons.org/licenses/by-sa/3.0/

# Requirements:
1. A MariaDB database instance loaded with the Lahman Baseball Database. 
   - I used the 2019 MySQL load file (found at the link above) with the following changes:
     - Change the default character set from `utf8mb4` to `utf8`
     - Changed the collate from `utf8mb4_0900_ai_ci` to `utf8_general_ci`
   - MariaDB is running in a Docker container with reasonable default ports
   - Database is `lahmansbaseballdb`
2. A running Kafka broker at reasonable default ports
   - I'm running the Bitnami Kafka and Zookeeper Docker containers. The configuration is shown on Docker Hub at https://hub.docker.com/r/bitnami/kafka
   
