-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 20, 2023 at 03:06 PM
-- Server version: 5.7.9
-- PHP Version: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cycle_sharing`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `w_id` int(11) DEFAULT NULL,
  `cardnumber` varchar(100) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `expdate` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`account_id`, `w_id`, `cardnumber`, `amount`, `expdate`) VALUES
(1, 1, '1223214214111111', '1000', '2023-02-02'),
(2, 4, '1223214214111112', '1240', '2023-02-26');

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
CREATE TABLE IF NOT EXISTS `booking` (
  `b_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_id` int(11) DEFAULT NULL,
  `c_id` int(11) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `hours` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`b_id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`b_id`, `r_id`, `c_id`, `amount`, `hours`, `status`) VALUES
(25, 1, 9, '20', '1', 'Dropped'),
(24, 2, 7, '20', '1', 'Dropped'),
(23, 1, 7, '20', '1', 'Dropped'),
(22, 3, 6, '20', '1', 'Booked'),
(21, 3, 8, '40', '2', 'Dropped'),
(20, 2, 6, '20', '1', 'Dropped'),
(19, 2, 6, '40', '2', 'Dropped'),
(18, 2, 6, '20', '1', 'Dropped'),
(17, 2, 6, '20', '1', 'Dropped'),
(16, 2, 6, '20', '1', 'Dropped');

-- --------------------------------------------------------

--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
CREATE TABLE IF NOT EXISTS `complaint` (
  `co_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_id` int(11) DEFAULT NULL,
  `c_id` int(11) DEFAULT NULL,
  `hours` varchar(100) DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`co_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `complaint`
--

INSERT INTO `complaint` (`co_id`, `r_id`, `c_id`, `hours`, `complaint`) VALUES
(5, 2, 7, '1', 'bad cycle '),
(4, 1, 7, '1', 'not a good cycle');

-- --------------------------------------------------------

--
-- Table structure for table `cycle`
--

DROP TABLE IF EXISTS `cycle`;
CREATE TABLE IF NOT EXISTS `cycle` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `o_id` int(11) DEFAULT NULL,
  `s_id` int(11) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `cycle_name` varchar(100) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cycle`
--

INSERT INTO `cycle` (`c_id`, `o_id`, `s_id`, `latitude`, `longitude`, `cycle_name`, `image`, `status`) VALUES
(9, 4, 1, '9.9780225072082', '76.28380537033081', 'bike10', 'static/imagefbfc0a9b-9a51-4d8b-813d-6f61ce063c47cycle3.jpg', 'Avaliable'),
(8, 2, 3, '9.985342372933845', '76.2857055169322', 'mountain bike', 'static/image347e0493-8ee0-4f36-ad34-d6b7d2f7a349cycle3.jpg', 'Booked'),
(7, 1, 1, '9.985154853564417', '76.28921270370483', 'bike2', 'static/image73970477-3d71-401e-96b6-9fc403f13cabcycle2.webp', 'Avaliable'),
(6, 1, 1, '9.97995618099199', '76.28370881080627', 'bike1', 'static/imageb477ee0e-2717-4f1a-92e3-dce83dae334ccycle1.webp', 'Avaliable');

-- --------------------------------------------------------

--
-- Table structure for table `cycle_station`
--

DROP TABLE IF EXISTS `cycle_station`;
CREATE TABLE IF NOT EXISTS `cycle_station` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `sname` varchar(100) DEFAULT NULL,
  `no_of_cycle` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`s_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cycle_station`
--

INSERT INTO `cycle_station` (`s_id`, `sname`, `no_of_cycle`, `location`) VALUES
(1, 'station1', '3', 'ernakulam'),
(2, 'station2', '1', 'mg road'),
(3, 'station3', '0', 'aluva');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
CREATE TABLE IF NOT EXISTS `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `user_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`login_id`, `username`, `password`, `user_type`) VALUES
(1, 'admin', 'admin', 'admin'),
(5, 'owner', 'owner', 'owner'),
(6, 'rider', 'rider', 'Rider'),
(7, 'akshay', 'akshay', 'Rider'),
(8, 'akshayown', 'akshay', 'owner'),
(9, 'anu', 'anu', 'Rider'),
(10, 'nikhil', 'nikhil', 'opending'),
(11, 'steve', 'steve', 'owner');

-- --------------------------------------------------------

--
-- Table structure for table `owner`
--

DROP TABLE IF EXISTS `owner`;
CREATE TABLE IF NOT EXISTS `owner` (
  `o_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `f_name` varchar(100) DEFAULT NULL,
  `l_name` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `hname` varchar(100) DEFAULT NULL,
  `street` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`o_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `owner`
--

INSERT INTO `owner` (`o_id`, `login_id`, `f_name`, `l_name`, `city`, `hname`, `street`) VALUES
(1, 5, 'Amal', 'Raj', 'Ernakulam', 'Kochupurakkal', 'Kakkanad'),
(2, 8, 'Akshay', 'Akshay', 'Cherthala', 'Keenath', 'Pattanakkad'),
(3, 10, 'Nikhil', 'S', 'Ernakulam', 'nikhil house', 'kakkanad'),
(4, 11, 'Steve', 'R', 'Ernakulam', 'steve', 'kochi');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` varchar(100) DEFAULT NULL,
  `r_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `b_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`p_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`p_id`, `amount`, `r_id`, `date`, `b_id`) VALUES
(1, '20', 2, '2023-01-14', '16'),
(2, '20', 1, '2023-01-05', '4'),
(3, '40', 1, '2023-01-05', '2'),
(4, '20', 1, '2023-01-05', '3');

-- --------------------------------------------------------

--
-- Table structure for table `rider`
--

DROP TABLE IF EXISTS `rider`;
CREATE TABLE IF NOT EXISTS `rider` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `f_name` varchar(100) DEFAULT NULL,
  `l_name` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `hname` varchar(100) DEFAULT NULL,
  `street` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rider`
--

INSERT INTO `rider` (`r_id`, `login_id`, `f_name`, `l_name`, `city`, `hname`, `street`, `latitude`, `longitude`) VALUES
(1, 6, 'Jenisha', 'L', 'ernakulam', 'house', 'street', '10.03280999', '76.32589536'),
(3, 9, 'Anu', 'k', 'Ernakulam ', 'Anu house', 'kakkanad', '10.020889', '76.3391061'),
(2, 7, 'Akshay', 'K S', 'cherthala', 'keenath', 'pattanakkad ', '10.0329103', '76.3254671');

-- --------------------------------------------------------

--
-- Table structure for table `wallet`
--

DROP TABLE IF EXISTS `wallet`;
CREATE TABLE IF NOT EXISTS `wallet` (
  `w_id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` varchar(100) DEFAULT NULL,
  `login_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`w_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `wallet`
--

INSERT INTO `wallet` (`w_id`, `amount`, `login_id`) VALUES
(1, '240', 5),
(2, '920', 6),
(3, '760', 7),
(4, '40', 8),
(5, '880', 9),
(6, '0', 10),
(7, '20', 11);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
