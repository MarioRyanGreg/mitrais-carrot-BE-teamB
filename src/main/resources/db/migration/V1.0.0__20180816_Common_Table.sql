/*
    Table structure for table `users`
*/
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `active` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
    Table structure for table `users`
*/
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `role` VALUES (1,'Staff');
INSERT INTO `role` VALUES (2,'Admin');
INSERT INTO `role` VALUES (3,'Unknown');
INSERT INTO `role` VALUES (4,'Manager');
INSERT INTO `role` VALUES (5,'Employee');
INSERT INTO `role` VALUES (6,'RootAdmin');
INSERT INTO `role` VALUES (7,'Stakeholder');
INSERT INTO `role` VALUES (8,'SeniorManager');

/*
    Table structure for table `users`
*/
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
    Table structure for table `sharing_level`
*/
CREATE TABLE `sharing_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grade` varchar(50) DEFAULT NULL,
  `sharing_level` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
    Table structure for table `manager_rewards`
*/
CREATE TABLE `manager_rewards` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) DEFAULT NULL,
  `carrot` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
    Table structure for table `stockist_rewards`
*/
CREATE TABLE `stockist_rewards` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) DEFAULT NULL,
  `carrot` int(11) DEFAULT NULL,
  `status` smallint(6) DEFAULT 1,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/*
    Table structure for table `bazaar`
*/
CREATE TABLE `bazaar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bazar_name` varchar(50) DEFAULT NULL,
  `start_period` date DEFAULT NULL,
  `end_period` date DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
    Table structure for table `bazaar`
*/
CREATE TABLE `bazaar_transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bazaar_id` int(11) DEFAULT NULL,
  `total_item` int(11) DEFAULT NULL,
  `auto_approve` varchar(10) DEFAULT NULL,
  `item_on_sale` varchar(10) DEFAULT NULL,
  `sold` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/*
    Table structure for table `bazaar`
*/
CREATE TABLE `bazaar_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL, 
  `exchange_rate` float DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
    Table structure for table `employee_transactions`
*/
CREATE TABLE `employee_transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_type` varchar(10) DEFAULT NULL,
  `transaction_to` varchar(10) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `carrot` int(11) DEFAULT NULL,
  `transaction_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
    Table structure for table `freezer`
*/
CREATE TABLE `freezer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `barn_name` varchar(50) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `released` varchar(50) DEFAULT NULL,
  `transaction_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/*
    Table structure for table `barn`
*/
CREATE TABLE `barn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `barn_name` varchar(50) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `carrot_per_employee` int(11) DEFAULT NULL,
  `transaction_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
    Table structure for table `barn`
*/
CREATE TABLE `barn_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) DEFAULT NULL,
  `recipient_id` int(11) DEFAULT NULL,
  `carrot_type` varchar(50) DEFAULT NULL,
  `carrot_total` int(11) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `transaction_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
