-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 19-12-2012 a las 23:35:50
-- Versión del servidor: 5.5.16
-- Versión de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `ProyectoTwitter`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eventos`
--

DROP TABLE IF EXISTS `eventos`;
CREATE TABLE IF NOT EXISTS `eventos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_autor` int(10) unsigned NOT NULL,
  `id_destinatario` int(10) unsigned NOT NULL,
  `id_tweet` int(10) unsigned NOT NULL,
  `tipo` varchar(15) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=191 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `favoritos`
--

DROP TABLE IF EXISTS `favoritos`;
CREATE TABLE IF NOT EXISTS `favoritos` (
  `id_usuario` int(10) unsigned NOT NULL,
  `id_tweet` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_tweet`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hashtag`
--

DROP TABLE IF EXISTS `hashtag`;
CREATE TABLE IF NOT EXISTS `hashtag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `texto` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hashtagsTweets`
--

DROP TABLE IF EXISTS `hashtagsTweets`;
CREATE TABLE IF NOT EXISTS `hashtagsTweets` (
  `id_hashtag` int(10) unsigned NOT NULL,
  `id_tweet` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_hashtag`,`id_tweet`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensajes`
--

DROP TABLE IF EXISTS `mensajes`;
CREATE TABLE IF NOT EXISTS `mensajes` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_autor` int(10) unsigned NOT NULL,
  `id_destinatario` int(10) unsigned NOT NULL,
  `texto` text COLLATE utf8_spanish2_ci NOT NULL,
  `fecha` int(10) unsigned NOT NULL,
  `inReplyTo` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_autor` (`id_autor`),
  KEY `id_destinatario` (`id_destinatario`),
  KEY `fecha` (`fecha`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=13 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `places`
--

DROP TABLE IF EXISTS `places`;
CREATE TABLE IF NOT EXISTS `places` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_spanish2_ci NOT NULL DEFAULT 'vacio',
  `pais` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `ciudad` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `tipo` varchar(15) COLLATE utf8_spanish2_ci NOT NULL DEFAULT 'vacio',
  `longitud1` double NOT NULL,
  `latitud1` double NOT NULL,
  `longitud2` double NOT NULL,
  `latitud2` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `retweet`
--

DROP TABLE IF EXISTS `retweet`;
CREATE TABLE IF NOT EXISTS `retweet` (
  `id_usuario` int(10) unsigned NOT NULL,
  `id_tweet` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_tweet`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seguidores`
--

DROP TABLE IF EXISTS `seguidores`;
CREATE TABLE IF NOT EXISTS `seguidores` (
  `id_seguidor` int(10) unsigned NOT NULL,
  `id_seguido` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_seguidor`,`id_seguido`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitudesEnviadas`
--

DROP TABLE IF EXISTS `solicitudesEnviadas`;
CREATE TABLE IF NOT EXISTS `solicitudesEnviadas` (
  `id_interesado` int(11) NOT NULL,
  `id_requerido` int(11) NOT NULL,
  PRIMARY KEY (`id_interesado`,`id_requerido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tweet`
--

DROP TABLE IF EXISTS `tweet`;
CREATE TABLE IF NOT EXISTS `tweet` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `texto` text COLLATE utf8_spanish2_ci NOT NULL,
  `autor` int(10) unsigned NOT NULL,
  `fecha` int(10) unsigned NOT NULL,
  `inReplyTo` int(10) unsigned NOT NULL,
  `placeID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `autor` (`autor`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=61 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `screenName` varchar(15) COLLATE utf8_spanish2_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_spanish2_ci NOT NULL DEFAULT '',
  `email` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `password` char(40) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha_registro` int(10) unsigned NOT NULL,
  `descripcion` text COLLATE utf8_spanish2_ci NOT NULL,
  `location` varchar(30) COLLATE utf8_spanish2_ci NOT NULL DEFAULT '',
  `profileBackgroundImageUrl` varchar(50) COLLATE utf8_spanish2_ci NOT NULL DEFAULT '',
  `profileImageUrl` varchar(50) COLLATE utf8_spanish2_ci NOT NULL DEFAULT '',
  `id_status` int(11) unsigned NOT NULL DEFAULT '0',
  `protectedUser` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=9 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
