-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.7.23-log - MySQL Community Server (GPL)
-- Операционная система:         Win32
-- HeidiSQL Версия:              9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Дамп данных таблицы conference_db.application: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` (`id`, `section_id`, `report_id`, `report_date`, `status_id`) VALUES
	(1, 1, 1, 1539932400, 2),
	(2, 3, 2, 1539932400, 2),
	(3, 2, 3, 1540040400, 2),
	(4, 1, 4, 1540018800, 2),
	(5, 3, 5, NULL, 1),
	(6, 2, 6, 1540036800, 2),
	(7, 1, 7, NULL, 3),
	(8, 5, 8, 1541833800, 1);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;

-- Дамп данных таблицы conference_db.conference: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `conference` DISABLE KEYS */;
INSERT INTO `conference` (`id`, `name`, `startdate`, `enddate`, `location`, `description`) VALUES
	(1, 'Joker', 1539925200, 1540054800, 'Saint Petersburg, Expoforum', 'Joker is a large international Java conference for Senior Java developers. Joker grows, becomes even more exciting every year. The conference brings together more than 1000 participants.'),
	(2, 'Blockchain & Bitcoin Conference Belarus 2018', 1539154800, 1539183600, 'Minsk, Belarus', 'Key topics: \n-legislation: future prospects of crypto business in Belarus;\n- blockchain-based business solutions;\n- cryptocurrencies: forecasts for 2018;\n- ICO for investors and organizers.'),
	(3, 'XXIII Белорусский энергетический и экологический форум', 1541739600, 1542027600, 'Минск, Футбольный манеж', 'Форум посвящен таким современным технологическим трендам, как технологии интернета вещей в энергетике; умной энергетики для промышленных производств, жилых зданий и городов; технологическим решениям для энергетики.');
/*!40000 ALTER TABLE `conference` ENABLE KEYS */;

-- Дамп данных таблицы conference_db.message: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` (`time`, `user_from`, `user_to`, `text`, `is_read`) VALUES
	(1539679469, 3, 1, 'Hello', b'0'),
	(1539679491, 3, 1, 'Have new report for conf!', b'0'),
	(1539679568, 1, 4, 'Do you have new report?', b'0');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;

-- Дамп данных таблицы conference_db.report: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` (`id`, `user_id`, `report_name`, `description`) VALUES
	(1, 3, 'Cloud native Java EE', 'This talk shows what it takes to implement cloud-ready, adaptive and scalable applications using Java EE, which extensions are out there that help us do the job and why Java EE perfectly fits the container and orchestration world.'),
	(2, 4, 'Java 9 Modules. Why not OSGi?', 'Modules will inevitably appear in Java 9. Some people are little worried about it, some are scared, and some genuinely perplexed: why do we need them, if we have OSGi for more than 15 years that solves exactly the same problems as Java 9 modules?'),
	(3, 5, 'Patterns and Аnti-patterns in Java 8', 'Java 8 is being around for a while already and lot of us are already using Java 8 features on our projects. But do we use these great Java 8 features correctly and efficiently? Having done lots of code reviews during last years I have seen some common antipatterns of Java 8 features usage. In this talk I want to show you some of the examples where Java 8 features were misused or poorly used and show you how certain things could have been better implemented.'),
	(4, 6, 'Concurrency For Humans', 'When you open the concurrency chapter of your favorite Java book, you\'ll learn about threads, synchronized methods, wait, and notify. But that\'s not how you should write concurrent code. As you will see in this talk, you should think in terms of tasks, not threads. Use threadsafe data structures and parallel streams, and use them correctly. And embrace asynchronous processing. The talk shows how the Java standard library gives you all the tools you need for common coding patterns, without ever having to resort to locks or conditions.'),
	(5, 7, 'Runtime vs. compile time (JIT vs. AOT) optimizations in Java and C++', 'The talk reveals how Just In Time Compiler (e.g. JIT C2) from HotSpot/OpenJDK internally manages runtime optimizations for hot methods in comparison to ahead of time approach triggered by LLVM clang on similar C++ source code, emphasizing all of the internals and strategies used by each Compiler to achieve better performance. For each optimization there is a similar Java and C++ source code and corresponding generated assembly code in order to prove what really happens under the hood. Each test is covered by a dedicated language benchmark and conclusions.'),
	(6, 8, 'Java Code Coverage mechanics', 'JaCoCo is a widely adopted code coverage library for the JVM which is integrated in many build tools and IDEs. In this talk the two core authors Evgeny and Marc will explore the implementation details of the library. As JaCoCo is fully based on Java bytecode we will do a deep dive into some internals of the JVM, different Java compilers and into corner cases of the respective specifications and implementations.'),
	(7, 9, 'JUnit 5 extensions: from conditional test execution to test templates', 'add later'),
	(8, 3, 'Новая энергетическая политика в городах', 'В ходе дискуссии основное внимание будет уделено тому, как реализуется Парижское соглашение в Беларуси и Германии? Какова роль городов в процессе его реализации? Как можно сделать энергоснабжение городов экологичным и эффективным? Какие мероприятия способствуют вовлечению возобновляемых источников энергии, эффективному использованию энергии и энергосбережению в Беларуси и Германии? Какие вызовы связаны с повышением энергоэффективности в секторе зданий? ');
/*!40000 ALTER TABLE `report` ENABLE KEYS */;

-- Дамп данных таблицы conference_db.role: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `name`) VALUES
	(1, 'ADMIN'),
	(2, 'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Дамп данных таблицы conference_db.section: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` (`id`, `name`, `conference_id`, `description`) VALUES
	(1, 'Introduction to technology', 1, 'Introduction new Java trend'),
	(2, 'For practicing engineers', 1, 'Intresting thing for Java mind'),
	(3, 'Hardcore', 1, 'Will be code too much'),
	(4, 'Main section', 2, 'Have one section'),
	(5, 'Основная секция', 3, 'Современные тенденции энергетики');
/*!40000 ALTER TABLE `section` ENABLE KEYS */;

-- Дамп данных таблицы conference_db.status: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` (`id`, `name`) VALUES
	(2, 'Approved'),
	(5, 'Canceled by user'),
	(3, 'Need to change'),
	(1, 'New report'),
	(4, 'Rejected');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;

-- Дамп данных таблицы conference_db.user: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `login`, `password`, `firstname`, `lastname`, `role_id`, `email`, `phone`, `create_time`, `is_blocked`) VALUES
	(1, 'administrators', '', 'administrators', 'administrators', 1, 'administrators@gmail.com', '+375297777777', 0, b'0'),
	(2, 'admin', 'f6fdffe48c908deb0f4c3bd36c032e72', 'Admin', 'Admin', 1, 'admin@gmail.com', '+375291234567', 1534670723, b'0'),
	(3, 'user', '5cc32e366c87c4cb49e4309b75f57d64', 'User', 'User', 2, 'user@gmail.com', '+375291111111', 1534970723, b'0'),
	(4, 'miron', '02005f7a032c86959defe3391e77daed', 'Dmitry', 'Mironovich', 2, 'dmitrymironov@gmail.com', '+375297353879', 1534770723, b'0'),
	(5, 'evilgenius', 'bb95c4046e3ba350e5518a39ff408854', 'Vladimir', 'Malafei', 2, 'malafey38@mail.ru', '+375293759262', 1534771074, b'0'),
	(6, 'victor88', '932f9b6170a0d623e430fbc4ca9d5494', 'Victor', 'Matusevich', 2, 'matusevic85@gmail.com', '+375297346245', 1534771074, b'0'),
	(7, 'kazak68', '0c5ca5b1c4dd0aaac2bf286cf82b9a8a', 'Pavel', 'Barsuk', 2, 'kazak68@tut.by', '+375291725962', 1534771595, b'0'),
	(8, 'nataska', '7d058550bb78f557abd1bcf815c39690', 'Natalya', 'Shliapik', 2, 'ShliapikN@gmail.com', '+375295825487', 1534771595, b'0'),
	(9, 'maxim27', '9704520fcd6ea22952e00d925c7ccb58', 'Max', 'Petrov', 2, 'petrov@tut.by', '+375297344218', 1534834825, b'0'),
	(10, 'ivanTP', '6d3b5f74484c88c898eba55f3c30290a', 'Ivan', 'Ivanov', 2, 'ivanov@mail.ru', '+375295825812', 1534835004, b'0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
