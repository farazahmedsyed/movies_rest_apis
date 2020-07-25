insert into users (name)
values ('Director'),('Actor1'),('Actor2'),('DirectorActor');

INSERT INTO `movies` (`rank`,director_id, `title`, `genre`, `year`, `run_time`, `rating`, `description`)
VALUES
(1,1,'Movie2012','a',2012,'11',2,'qqqqqqqq'),
(2,1,'Movie2013','no genre',2013,'12',3,'aaaa'),
(2,1,'Movie2014','Cartoon',2014,'1hr',4,'Nice'),
(11,1,'Movie2015','Fight Cartoon',2015,'2 hr',4.5,'Cartoon'),
(9,1,'Movie2016','Fight Cartoon',2016,'2 hr',4.5,'Cartoon'),
(3,1,'Movie2017','Fight Cartoon',2017,'2 hr',4.5,'Cartoon');

insert into movie_actors (user_id, movie_id)
values (1,1),(2,1),(3,3)