-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 11-12-2012 a las 17:42:12
-- Versión del servidor: 5.5.27
-- Versión de PHP: 5.4.7

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

CREATE TABLE IF NOT EXISTS `eventos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_autor` int(10) unsigned NOT NULL,
  `id_destinatario` int(10) unsigned NOT NULL,
  `id_tweet` int(10) unsigned NOT NULL,
  `tipo` varchar(15) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=62 ;

--
-- Volcado de datos para la tabla `eventos`
--

INSERT INTO `eventos` (`id`, `id_autor`, `id_destinatario`, `id_tweet`, `tipo`, `fecha`) VALUES
(56, 4, 4, 40, '1', 1355164835),
(55, 4, 4, 42, '1', 1355164291),
(54, 1, 4, 40, '3', 1355163408),
(53, 1, 4, 40, '1', 1355163404),
(52, 1, 4, 40, '3', 1355160254),
(51, 1, 4, 40, '1', 1355160245),
(50, 1, 4, 40, '3', 1355159908),
(49, 1, 4, 40, '1', 1355156668),
(48, 1, 4, 0, '2', 1355153847),
(47, 1, 4, 0, '2', 1355153135),
(57, 1, 4, 42, '1', 1355164958),
(58, 1, 4, 42, '3', 1355164960),
(59, 1, 4, 40, '1', 1355164962),
(60, 1, 4, 40, '3', 1355168662),
(61, 1, 4, 40, '1', 1355168664);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `favoritos`
--

CREATE TABLE IF NOT EXISTS `favoritos` (
  `id_usuario` int(10) unsigned NOT NULL,
  `id_tweet` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_tweet`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `favoritos`
--

INSERT INTO `favoritos` (`id_usuario`, `id_tweet`) VALUES
(1, 40),
(4, 40),
(4, 42);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hashtag`
--

CREATE TABLE IF NOT EXISTS `hashtag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `texto` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hashtagsTweets`
--

CREATE TABLE IF NOT EXISTS `hashtagsTweets` (
  `id_hashtag` int(10) unsigned NOT NULL,
  `id_tweet` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_hashtag`,`id_tweet`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensajes`
--

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

--
-- Volcado de datos para la tabla `mensajes`
--

INSERT INTO `mensajes` (`id`, `id_autor`, `id_destinatario`, `texto`, `fecha`, `inReplyTo`) VALUES
(11, 1, 2, 'esto es un mensaje para kmilinho', 1353509601, 0),
(10, 1, 2, 'esto es un mensaje para kmilinho', 1353507332, 0),
(12, 1, 4, ' Caca', 1355080433, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `places`
--

CREATE TABLE IF NOT EXISTS `places` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `pais` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `ciudad` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `tipo` varchar(15) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `longitud1` double NOT NULL,
  `latitud1` double NOT NULL,
  `longitud2` double NOT NULL,
  `latitud2` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `places`
--

INSERT INTO `places` (`id`, `name`, `pais`, `ciudad`, `tipo`, `longitud1`, `latitud1`, `longitud2`, `latitud2`) VALUES
(1, '', 'España', 'Madrid', '', 15, 14, 13, 55),
(4, NULL, 'Spain', 'Fuenlabrada', NULL, -3.7931, 40.285199, -3.7950999999999997, 40.283199);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `retweet`
--

CREATE TABLE IF NOT EXISTS `retweet` (
  `id_usuario` int(10) unsigned NOT NULL,
  `id_tweet` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_tweet`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seguidores`
--

CREATE TABLE IF NOT EXISTS `seguidores` (
  `id_seguidor` int(10) unsigned NOT NULL,
  `id_seguido` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_seguidor`,`id_seguido`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `seguidores`
--

INSERT INTO `seguidores` (`id_seguidor`, `id_seguido`) VALUES
(1, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tweet`
--

CREATE TABLE IF NOT EXISTS `tweet` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `texto` varchar(140) COLLATE utf8_spanish2_ci NOT NULL,
  `autor` int(10) unsigned NOT NULL,
  `fecha` int(10) unsigned NOT NULL,
  `inReplyTo` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `autor` (`autor`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=43 ;

--
-- Volcado de datos para la tabla `tweet`
--

INSERT INTO `tweet` (`id`, `texto`, `autor`, `fecha`, `inReplyTo`) VALUES
(39, 'Soy la hostia', 4, 1355081559, 0),
(41, 'LOL me gusta caca', 1, 1355088091, 0),
(40, '@Antonio mola', 4, 1355086549, 0),
(42, '@antonio mola que te cagas', 4, 1355088249, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `screenName` varchar(15) COLLATE utf8_spanish2_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_spanish2_ci NOT NULL DEFAULT '',
  `email` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `password` char(40) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha_registro` int(10) unsigned NOT NULL,
  `descripcion` varchar(60) COLLATE utf8_spanish2_ci NOT NULL DEFAULT '',
  `location` varchar(30) COLLATE utf8_spanish2_ci NOT NULL DEFAULT '',
  `profileBackgroundImageUrl` varchar(50) COLLATE utf8_spanish2_ci NOT NULL DEFAULT '',
  `profileImageUrl` varchar(50) COLLATE utf8_spanish2_ci NOT NULL DEFAULT '',
  `web_link` varchar(50) COLLATE utf8_spanish2_ci NOT NULL DEFAULT '',
  `id_status` int(11) unsigned NOT NULL DEFAULT '0',
  `protectedUser` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `screenName`, `name`, `email`, `password`, `fecha_registro`, `descripcion`, `location`, `profileBackgroundImageUrl`, `profileImageUrl`, `web_link`, `id_status`, `protectedUser`) VALUES
(1, 'Antonio', 'Antonio Fernandez', 'email@gmail.com', '7f4b12a90500708eb2dacde70df4124f05685048', 0, 'Me gusta la magia negra', '', '', '', 'UNKNOWN', 0, 0),
(2, 'kmilinho', 'Camilo Pereira', 'c.pereira.leon@gmail.com', '7f4b12a90500708eb2dacde70df4124f05685048', 0, '', '', '', '', '', 0, 0),
(4, 'Batman', 'BatmanSoyLaHostia', 'batman@caca.com', '352c45c8d732a6fbbc554fdb2b13a8775a87aea7', 1355080340, '', '', '', '', 'UNKNOWN', 0, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
