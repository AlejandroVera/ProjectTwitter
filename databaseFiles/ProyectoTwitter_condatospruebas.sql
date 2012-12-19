-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 19-12-2012 a las 23:36:16
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

--
-- Volcado de datos para la tabla `eventos`
--

INSERT INTO `eventos` (`id`, `id_autor`, `id_destinatario`, `id_tweet`, `tipo`, `fecha`) VALUES
(112, 5, 6, 0, '2', 1355311643),
(111, 6, 5, 0, '5', 1355311636),
(110, 5, 0, 0, '4', 1355311631),
(109, 5, 0, 0, '4', 1355311566),
(108, 5, 0, 0, '4', 1355311547),
(107, 5, 0, 0, '4', 1355311520),
(106, 5, 0, 0, '4', 1355311306),
(105, 5, 0, 0, '4', 1355311271),
(104, 5, 0, 0, '4', 1355311251),
(103, 5, 0, 0, '4', 1355311226),
(102, 5, 0, 0, '4', 1355311199),
(113, 7, 6, 0, '2', 1355341926),
(114, 7, 0, 0, '4', 1355341970),
(115, 7, 0, 0, '4', 1355745900),
(116, 7, 0, 0, '4', 1355854710),
(117, 7, 0, 0, '4', 1355854852),
(118, 7, 0, 0, '4', 1355854872),
(119, 7, 0, 0, '4', 1355855020),
(120, 7, 0, 0, '4', 1355855267),
(121, 7, 0, 0, '4', 1355855303),
(122, 7, 0, 0, '4', 1355855801),
(123, 7, 0, 0, '4', 1355856129),
(124, 8, 7, 0, '2', 1355856324),
(125, 7, 8, 0, '2', 1355858372),
(126, 7, 8, 0, '6', 1355858373),
(127, 7, 6, 0, '6', 1355858423),
(128, 7, 5, 0, '5', 1355858430),
(129, 7, 0, 0, '4', 1355858430),
(130, 7, 6, 0, '2', 1355858433),
(131, 7, 6, 0, '6', 1355858434),
(132, 7, 6, 0, '2', 1355858436),
(133, 7, 6, 0, '6', 1355858436),
(134, 7, 8, 0, '2', 1355858438),
(135, 7, 8, 0, '6', 1355858439),
(136, 7, 6, 0, '2', 1355858952),
(137, 7, 6, 0, '6', 1355858952),
(138, 7, 8, 0, '2', 1355858953),
(139, 7, 8, 0, '6', 1355858954),
(140, 7, 8, 0, '2', 1355858958),
(141, 7, 6, 0, '2', 1355858960),
(142, 7, 8, 0, '6', 1355858961),
(143, 7, 6, 0, '6', 1355858962),
(144, 7, 8, 0, '2', 1355858963),
(145, 7, 8, 0, '6', 1355858964),
(146, 8, 7, 0, '6', 1355859090),
(147, 8, 7, 0, '2', 1355859093),
(148, 7, 8, 0, '2', 1355859348),
(149, 7, 8, 0, '6', 1355859349),
(150, 7, 8, 0, '2', 1355859592),
(151, 7, 8, 0, '6', 1355859593),
(152, 7, 6, 0, '2', 1355859594),
(153, 7, 6, 0, '6', 1355859595),
(154, 7, 8, 0, '2', 1355859603),
(155, 7, 8, 0, '6', 1355859603),
(156, 8, 6, 0, '2', 1355859743),
(157, 8, 6, 0, '6', 1355859745),
(158, 8, 6, 0, '2', 1355859746),
(159, 8, 6, 0, '6', 1355859748),
(160, 8, 6, 0, '2', 1355859749),
(161, 8, 6, 0, '6', 1355859750),
(162, 8, 7, 0, '6', 1355859750),
(163, 8, 7, 0, '2', 1355859751),
(164, 8, 6, 0, '2', 1355859752),
(165, 8, 6, 0, '6', 1355859753),
(166, 8, 7, 0, '6', 1355859754),
(167, 8, 6, 0, '2', 1355859759),
(168, 8, 6, 0, '6', 1355859759),
(169, 8, 6, 0, '2', 1355859814),
(170, 8, 6, 0, '6', 1355859816),
(171, 8, 6, 0, '2', 1355859827),
(172, 8, 6, 0, '6', 1355859828),
(173, 8, 7, 0, '2', 1355859829),
(174, 8, 7, 0, '6', 1355859829),
(175, 8, 6, 0, '2', 1355859972),
(176, 8, 6, 0, '6', 1355859973),
(177, 8, 6, 0, '2', 1355860028),
(178, 8, 6, 0, '6', 1355860028),
(179, 8, 6, 0, '2', 1355860032),
(180, 8, 6, 0, '6', 1355860032),
(181, 8, 6, 0, '2', 1355860108),
(182, 8, 6, 0, '6', 1355860110),
(183, 8, 6, 0, '2', 1355860271),
(184, 8, 6, 0, '6', 1355860272),
(185, 8, 6, 0, '2', 1355860275),
(186, 8, 6, 0, '6', 1355860275),
(187, 8, 6, 0, '2', 1355860277),
(188, 8, 6, 0, '6', 1355860282),
(189, 8, 6, 0, '2', 1355860284),
(190, 8, 6, 0, '6', 1355860299);

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

--
-- Volcado de datos para la tabla `places`
--

INSERT INTO `places` (`id`, `name`, `pais`, `ciudad`, `tipo`, `longitud1`, `latitud1`, `longitud2`, `latitud2`) VALUES
(5, '', 'Spain', 'City', '', -3.999, 40.001, -4.001, 39.999),
(6, 'vacio', 'Spain', 'Valencia', 'vacio', -0.3657, 39.467701999999996, -0.3677, 39.465702);

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

--
-- Volcado de datos para la tabla `retweet`
--

INSERT INTO `retweet` (`id_usuario`, `id_tweet`) VALUES
(7, 58),
(8, 59);

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

--
-- Volcado de datos para la tabla `seguidores`
--

INSERT INTO `seguidores` (`id_seguidor`, `id_seguido`) VALUES
(5, 6);

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

--
-- Volcado de datos para la tabla `solicitudesEnviadas`
--

INSERT INTO `solicitudesEnviadas` (`id_interesado`, `id_requerido`) VALUES
(7, 5);

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

--
-- Volcado de datos para la tabla `tweet`
--

INSERT INTO `tweet` (`id`, `texto`, `autor`, `fecha`, `inReplyTo`, `placeID`) VALUES
(48, 'Tweet 1', 7, 1355341899, 0, -1),
(49, 'Tweet 2', 7, 1355341905, 0, -1),
(50, 'Otro mas', 7, 1355342078, 0, -1),
(51, 'Y otra para retrasarlo...', 7, 1355342087, 0, -1),
(52, '@borja Y a ti te mando otro', 7, 1355342111, 0, -1),
(53, '@borja Y a ti te mando otro', 7, 1355342115, 0, -1),
(54, '@borja Y a ti te mando otro', 7, 1355342127, 0, -1),
(55, '@borja Y a ti te mando otro', 7, 1355342127, 0, -1),
(56, '@borja Y a ti te mando otro', 7, 1355342127, 0, -1),
(57, 'sdasdasda', 7, 1355506078, 0, -1),
(58, '@Antonio @jaoijsñoi #borjatonto www.google.com', 6, 1355745991, 0, -1),
(59, 'Para que xafilox lo retweetee', 7, 1355856353, 0, -1),
(60, 'asasdas', 8, 1355956013, 0, -1);

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

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `screenName`, `name`, `email`, `password`, `fecha_registro`, `descripcion`, `location`, `profileBackgroundImageUrl`, `profileImageUrl`, `id_status`, `protectedUser`) VALUES
(6, 'borja', 'borja', 'ddd', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', 1355267793, '', '', '', 'http://imagestoge.twt/hJGRp4a', 0, 0),
(5, 'kmi', 'kmi', 'abc', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', 1355266713, '', '', '', 'http://imagestoge.twt/KgZUTSK', 0, 1),
(7, 'Antonio', 'Antonio Fernandez', 'antonio@antitonio.com', '7f4b12a90500708eb2dacde70df4124f05685048', 1355317532, 'Prueba de descripcion de un usuario en la que cuento toda mi vida y parte de otra para que este mensaje sea lo suficientemente largo como para que se tenga que ', '', '', 'http://imagestoge.twt/X7FPEj9', 0, 0),
(8, 'xafilox', 'xafilox', 'xafilox@gmail.com', '1b19a5dfea5827cccfb1d4d58498175b13347dea', 1355856302, '', '', '', '', 0, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
