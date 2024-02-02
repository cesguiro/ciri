-- Editoriales
INSERT INTO publishers (name) VALUES
                                 ('Editorial Anagrama'),
                                 ('Penguin Random House Grupo Editorial España'),
                                 ('Editorial Planeta, S. A.'),
                                 ('Debolsillo'),
                                 ('Minotauro'),
                                 ('Roca Editorial de Libros'),
                                 ('Rocabolsillo'),
                                 ('Bibliópolis');

-- Autores
INSERT INTO authors (name, nationality, birth_year, death_year) VALUES
                                                                    ('John Kennedy Toole', 'Estadounidense', 1937, 1969),
                                                                    ('Umberto Eco', 'Italiano', 1932, 2016),
                                                                    ('Milan Kundera', 'Checo', 1929, NULL),
                                                                    ('Terry Pratchett', 'Británico', 1948, 2015),
                                                                    ('Neil Gaiman', 'Británico', 1960, NULL),
                                                                    ('Douglas Adams', 'Británico', 1952, 2001),
                                                                    ('Andrzej Sapkowski', 'Polaco', 1948, NULL);

INSERT INTO books (isbn, title, synopsis, publisher_id, price, cover) VALUES
                                                                          ('9788433920423', 'La conjura de los necios', 'El protagonista de esta novela es uno de los personajes más memorables de la literatura norteamericana: Ignatius Reilly --una mezcla de Oliver Hardy delirante, Don Quijote adiposo y santo Tomás de Aquino perverso, reunidos en una persona--, que a los treinta años aún vive con su estrafalaria madre, está ocupado en escribir una extensa y demoledora denuncia contra nuestro siglo, tan carente de "teología geometría" como de "decencia y buen gusto"; un alegato desquiciado contra una sociedad desquiciada. Por una inesperada necesidad de dinero, se ve "catapultado en la fiebre de la existencia contemporánea", fiebre a la que Ignatius añadirá unos cuantos grandes más.', 1, 13.20, 'necios.jpeg'),
                                                                          ('9788426418197', 'El nombre de la rosa', 'Valiéndose de las características de la novela gótica, la crónica medieval y la novela policíaca, El nombre de la rosa narra las investigaciones detectivescas que realiza el fraile franciscano Guillermo de Baskerville para esclarecer los crímenes cometidos en una abadía benedictina en el año 1327. Le ayudará en su labor el novicio Adso, un joven que se enfrenta por primera vez a las realidades de la vida situadas más allá de las puertas del convento.', 2, 12.30, 'nombreRosa.jpeg'),
                                                                          ('9786074213485', 'La insorportable levedad del ser', 'La insoportable levedad del ser es una novela del escritor checo Milan Kundera, publicada en 1984. Ambientada en Praga durante 1968, La insoportable levedad del ser trata de un hombre y sus dudas existenciales en torno a la vida en pareja, convertidas en conflictos sexuales y afectivos.', 3, 18.05, 'levedad.jpeg'),
                                                                          ('9788466338141', 'La isla del día de antes', 'En el verano de 1963 un náufrago, Roberto de la Grive, llega a una nave abandonada en los Mares del Sur donde encuentra sólo animales desconocidos y máquinas extrañas. Frente a la nave hay una isla de ensueño, tan cercana como inalcanzable. Confinado en este exiguo espacio y perdido en el vasto mar, Roberto nos pone al corriente sobre su pasado a través de las cartas que escribe a una enigmática «Señora». Pero Roberto ha viajado hasta allí con una misión muy concreta: resolver el misterio por el cual pugnan las nuevas potencias de la época, el secreto del Punto Fijo.', 4, 14.20, 'islaDiaAntes.jpeg'),
                                                                          ('9788448022440', 'Buenos presagios', 'Según Las Buenas y Acertadas profecías de Agnes la Chalada, Bruja (el único libro fiable de profecías, escrito en 1655, antes de que ella explotara), el fin del mundo tendrá lugar el próximo sábado. Los ejércitos del Bien y del Mal se están agrupando, la Atlántida está resurgiendo, llueven sapos y los ánimos están algo alterados así que... todo parece ajustarse al Plan Divino. De no ser por un ángel quisquilloso y un demonio buscavidas que han vivido a costa de los mortales desde el comienzo de los tiempos y que no están dispuestos a aceptar tan fácilmente eso del «Fin de la civilización tal y como la conocemos». Y... ¡vaya por Dios! ¡Parece que alguien ha hecho desaparecer al Anticristo!', 5, 9.45, 'buenosPresagios.jpeg'),
                                                                          ('9788499085944', 'El país del fin del mundo', 'Este no es un libro sobre Australia, sino sobre un país totalmente distinto que,sin embargo y por pura casualidad, en ciertos aspectos resulta un poco... australiano. Se trata de un territorio seco y caluroso en el que todo lo que no es ponzoñoso es venenoso. Pero, aun así, es un bonito lugar, y está amenazado de muerte. ¿Conseguirá salvarlo el inepto mago Rincewind?', 4, 9.45, 'paisFinMundo.jpeg'),
                                                                          ('9788499086460', 'Mort', 'Mort es una novela de fantasía escrita por Terry Pratchett y publicada en 1987. Es la cuarta entrega de la saga del Mundodisco. En ella, la Muerte, presente en todas las novelas de la saga, desempeña por primera vez un papel protagonista. Ha sido adaptada como obra de teatro.​', 2, 10.40, 'mort.jpeg'),
                                                                          ('9788433927996', 'Guía del autoestopista galáctico', 'Un jueves a la hora de comer, la Tierra es inesperadamente demolida para poder construir una nueva autopista hiperespacial. Arthur Dent, un tipo de lo más corriente que esa misma mañana ha visto cómo echaban abajo su propia casa, considera que eso supera lo que una persona puede soportar. Pero, desgraciadamente, el fin de semana no ha hecho más que empezar, y la galaxia es un lugar extraño y sorprendente. Arthur huirá de la Tierra junto con un amigo suyo, Ford Prefect, que resultará ser un extraterrestre emparentado con Zaphod Beeblebrox, un pirata esquizoide de dos cabezas que fue Presidente de la Galaxia, en cuya nave Arthur conocerá al resto de personajes que lo acompañarán a lo largo de su periplo espacial: un androide paranoide y una terrícola que, como él, ha logrado escapar. Gracias a esta ineludible aventura, Dent descubrirá muchas cosas acerca de la existencia, pero, tal como aconseja la Guía del autoestopista galáctico, que no cunda el pánico.', 1, 12.25, 'guiaAutoestopista.jpeg'),
                                                                          ('9788499189390', 'Neverwhere', 'En el subsuelo de Londres, como debajo de cada gran ciudad, existe un mundo desconocido e invisible, plagado de extraños seres, en el que sobrevivir depende de abrir las puertas adecuadas... Hay mundos bajo tus pies, espías bajo las escaleras y formas que esperan al otro lado de los portales, que sólo has atisbado en tus sueños.', 6, 10.40, 'neverwhere.jpeg'),
                                                                          ('9788415729204', 'American Gods', 'Dioses antiguos, héroes mitológicos y figuras legendarias se dan cita en la obra narrativa más importante del extraordinario Neil Gaiman. Días antes de salir de prisión, la mujer de Sombra, Laura, muere en un misterioso accidente de coche. Aturdido por el dolor, emprende el regreso a casa. En el avión, se encontrará con el enigmático señor Miércoles, que dice ser un refugiado de una guerra antigua, un dios y también el rey de América. Juntos se embarcan en un viaje extraño a través de los Estados Unidos, mientras una tormenta de dimensiones épicas amenaza con desencadenarse.', 7, 20.80, 'americanGods.jpeg'),
                                                                          ('9788496173729', 'La espada del destino', 'La vida de un brujo cazador de monstruos no es fácil. Tan pronto puede uno tener que meterse hasta el cuello en un estercolero para eliminar a la bestia carroñera que amenaza la ciudad, intentado no atrapar una infección incurable, como se puede encontrar unido a la cacería de uno de los últimos dragones, en la que la cuestión no es si los cazadores conseguirán matar a la pobre bestia, sino qué pasará cuando tengan que repartirse el botín. Magos, príncipes, estarostas, voievodas, druidas, vexlings, dríadas, juglares y criaturas de todo pelaje pueblan esta tierra, enzarzados en conflictos de supervivencia, codicia y amor, y entre ellos avanza, solitario, el brujo Geralt de Rivia', 8, 22.75, 'espadaDestino.jpeg'),
                                                                          ('9788496173101', 'Tiempo de odio', 'Tiempo de odio es la segunda novela de La saga del brujo escrita por Andrzej Sapkowski. Es una secuela a los cuentos cortos recolectados en los libros El último deseo y La espada del destino, y a la novela La sangre de los elfos.', 8, 21.95, 'tiempoOdio.jpeg');


-- INSERT en la tabla books_authors
INSERT INTO book_authors (book_id, author_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 2),
    (5, 4),
    (5, 5),
    (6, 4),
    (7, 4),
    (8, 6),
    (9, 7),
    (10, 7);

-- Insertar usuarios con sus direcciones de entrega
INSERT INTO delivery_address (address, city, province, postal_code)
VALUES
    ('Calle Gran Vía 123', 'Valencia', 'Valencia', 46001),
    ('Calle Rambla 456', 'Alicante', 'Alicante', 03001),
    ('Calle Triana 789', 'Málaga', 'Málaga', 29001),
    ('Calle Alhambra 012', 'Zaragoza', 'Zaragoza', 50001);

INSERT INTO users (username, email, password, admin, delivery_address_id)
VALUES
    ('Usuario1', 'usuario1@example.com', 'password1', 0, 1), -- Asociado a la dirección en Valencia
    ('Usuario2', 'usuario2@example.com', 'password2', 0, 2), -- Asociado a la dirección en Alicante
    ('Usuario3', 'usuario3@example.com', 'password3', 1, 3), -- Asociado a la dirección en Málaga
    ('Usuario4', 'usuario4@example.com', 'password4', 0, 4); -- Asociado a la dirección en Zaragoza

INSERT INTO orders(order_date, user_id, status)
VALUES
    ('2022-01-15', 1, 0),
    ('2022-02-20', 2, 0),
    ('2022-03-10', 3, 1),
    ('2022-04-05', 4, 0);


INSERT INTO  booksWithoutId(isbn, title)
VALUES
    ('9788433920423', 'La conjura de los necios'),
    ('9788426418197', 'El nombre de la rosa'),
    ('9786074213485', 'La insorportable levedad del ser'),
    ('9788466338141', 'La isla del día de antes')