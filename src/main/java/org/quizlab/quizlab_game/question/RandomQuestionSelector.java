package org.quizlab.quizlab_game.question;

import java.util.Random;

public class RandomQuestionSelector {
	private Question[] questions;

	public RandomQuestionSelector() {
		questions = new Question[50];

		questions[0] = new Question("¿Cuál es la montaña más alta del mundo?",
				new String[] { "Monte Everest", "Monte Kilimanjaro", "Monte Aconcagua" }, "Monte Everest");
		questions[1] = new Question("¿En qué año se firmó la Declaración de Independencia de los Estados Unidos?",
				new String[] { "1776", "1789", "1812" }, "1776");
		questions[2] = new Question("¿Cuál es el elemento químico más abundante en el universo?",
				new String[] { "Hidrógeno", "Helio", "Oxígeno" }, "Hidrógeno");
		questions[3] = new Question("¿Cuál es la capital de España?", new String[] { "Madrid", "Barcelona", "Sevilla" },
				"Madrid");
		questions[4] = new Question("¿Quién escribió \"Don Quijote de la Mancha\"?",
				new String[] { "Miguel de Cervantes", "Gabriel García Márquez", "William Shakespeare" },
				"Miguel de Cervantes");
		questions[5] = new Question("¿Cuál es el país más grande de América del Sur?",
				new String[] { "Brasil", "Argentina", "Colombia" }, "Brasil");
		questions[6] = new Question("¿En qué año se llevó a cabo la Revolución Rusa?",
				new String[] { "1917", "1905", "1922" }, "1917");
		questions[7] = new Question("¿Cuál es el planeta más cercano al Sol?",
				new String[] { "Mercurio", "Venus", "Marte" }, "Mercurio");
		questions[8] = new Question("¿Quién escribió la novela \"Cien años de soledad\"?",
				new String[] { "Gabriel García Márquez", "Jorge Luis Borges", "Pablo Neruda" },
				"Gabriel García Márquez");
		questions[9] = new Question("¿Cuál es el símbolo químico del carbono?", new String[] { "C", "Ca", "Co" }, "C");
		questions[10] = new Question("¿Cuál es el continente más poblado del mundo?",
				new String[] { "Asia", "África", "Europa" }, "Asia");
		questions[11] = new Question("¿En qué año se llevó a cabo la Revolución Francesa?",
				new String[] { "1789", "1798", "1804" }, "1789");
		questions[12] = new Question("¿Cuál es el elemento químico más pesado?",
				new String[] { "Oganesón", "Plutonio", "Uranio" }, "Oganesón");
		questions[13] = new Question("¿Cuál es la capital de Japón?", new String[] { "Tokio", "Kioto", "Osaka" },
				"Tokio");
		questions[14] = new Question("¿Quién pintó \"La última cena\"?",
				new String[] { "Leonardo da Vinci", "Pablo Picasso", "Rembrandt" }, "Leonardo da Vinci");
		questions[15] = new Question("¿Cuál es el país más extenso de Europa?",
				new String[] { "Rusia", "Francia", "España" }, "Rusia");
		questions[16] = new Question("¿En qué año se llevó a cabo la Revolución Industrial?",
				new String[] { "Siglo XVIII", "Siglo XIX", "Siglo XX" }, "Siglo XVIII");
		questions[17] = new Question("¿Cuál es el planeta más alejado del Sol?",
				new String[] { "Neptuno", "Urano", "Plutón" }, "Neptuno");
		questions[18] = new Question("¿Quién escribió la tragedia \"Hamlet\"?",
				new String[] { "William Shakespeare", "Friedrich Nietzsche", "Miguel de Cervantes" },
				"William Shakespeare");
		questions[19] = new Question("¿Cuál es el símbolo químico del cobre?", new String[] { "Cu", "Co", "Cr" }, "Cu");
		questions[20] = new Question("¿Cuál es el desierto más grande del mundo?",
				new String[] { "Sahara", "Gobi", "Kalahari" }, "Sahara");
		questions[21] = new Question("¿En qué año se fundó la ciudad de Nueva York?",
				new String[] { "1624", "1776", "1848" }, "1624");
		questions[22] = new Question("¿Cuál es el elemento químico más reactivo?",
				new String[] { "Fluor", "Oxígeno", "Cloro" }, "Fluor");
		questions[23] = new Question("¿Cuál es la capital de México?",
				new String[] { "Ciudad de México", "Guadalajara", "Monterrey" }, "Ciudad de México");
		questions[24] = new Question("¿Quién pintó \"La noche estrellada\"?",
				new String[] { "Vincent van Gogh", "Pablo Picasso", "Claude Monet" }, "Vincent van Gogh");
		questions[25] = new Question("¿Cuál es el país más poblado de Europa?",
				new String[] { "Rusia", "Alemania", "Francia" }, "Rusia");
		questions[26] = new Question("¿En qué año se firmó el Tratado de Versalles?",
				new String[] { "1919", "1924", "1939" }, "1919");
		questions[27] = new Question("¿Cuál es el satélite natural de Marte?",
				new String[] { "Fobos", "Deimos", "Luna" }, "Fobos");
		questions[28] = new Question("¿Quién escribió \"Orgullo y prejuicio\"?",
				new String[] { "Jane Austen", "Charlotte Brontë", "Emily Dickinson" }, "Jane Austen");
		questions[29] = new Question("¿Cuál es el símbolo químico del plata?", new String[] { "Ag", "Au", "Pt" }, "Ag");
		questions[30] = new Question("¿Cuál es la cordillera más larga del mundo?",
				new String[] { "Los Andes", "Himalaya", "Montañas Rocosas" }, "Los Andes");
		questions[31] = new Question("¿En qué año se llevó a cabo la Revolución Industrial?",
				new String[] { "Siglo XVIII", "Siglo XIX", "Siglo XX" }, "Siglo XVIII");
		questions[32] = new Question("¿Cuál es el planeta más alejado del Sol?",
				new String[] { "Neptuno", "Urano", "Plutón" }, "Neptuno");
		questions[33] = new Question("¿Quién escribió la tragedia \"Hamlet\"?",
				new String[] { "William Shakespeare", "Friedrich Nietzsche", "Miguel de Cervantes" },
				"William Shakespeare");
		questions[34] = new Question("¿Cuál es el símbolo químico del cobre?", new String[] { "Cu", "Co", "Cr" }, "Cu");
		questions[35] = new Question("¿Cuál es el desierto más grande del mundo?",
				new String[] { "Sahara", "Gobi", "Kalahari" }, "Sahara");
		questions[36] = new Question("¿En qué año se fundó la ciudad de Nueva York?",
				new String[] { "1624", "1776", "1848" }, "1624");
		questions[37] = new Question("¿Cuál es el elemento químico más reactivo?",
				new String[] { "Fluor", "Oxígeno", "Cloro" }, "Fluor");
		questions[38] = new Question("¿Cuál es la capital de México?",
				new String[] { "Ciudad de México", "Guadalajara", "Monterrey" }, "Ciudad de México");
		questions[39] = new Question("¿Quién pintó \"La noche estrellada\"?",
				new String[] { "Vincent van Gogh", "Pablo Picasso", "Claude Monet" }, "Vincent van Gogh");
		questions[40] = new Question("¿Cuál es el país más poblado de Europa?",
				new String[] { "Rusia", "Alemania", "Francia" }, "Rusia");
		questions[41] = new Question("¿En qué año se firmó el Tratado de Versalles?",
				new String[] { "1919", "1924", "1939" }, "1919");
		questions[42] = new Question("¿Cuál es el satélite natural de Marte?",
				new String[] { "Fobos", "Deimos", "Luna" }, "Fobos");
		questions[43] = new Question("¿Quién escribió \"Orgullo y prejuicio\"?",
				new String[] { "Jane Austen", "Charlotte Brontë", "Emily Dickinson" }, "Jane Austen");
		questions[44] = new Question("¿Cuál es el símbolo químico del plata?", new String[] { "Ag", "Au", "Pt" }, "Ag");
		questions[45] = new Question("¿Cuál es el océano más grande del mundo?",
				new String[] { "Océano Pacífico", "Océano Atlántico", "Océano Índico" }, "Océano Pacífico");
		questions[46] = new Question("¿En qué año comenzó la Primera Guerra Mundial?",
				new String[] { "1914", "1918", "1939" }, "1914");
		questions[47] = new Question("¿Cuál es el elemento químico más ligero?",
				new String[] { "Hidrógeno", "Oxígeno", "Carbono" }, "Hidrógeno");
		questions[48] = new Question("¿Cuál es la capital de Francia?", new String[] { "París", "Londres", "Roma" },
				"París");
		questions[49] = new Question("¿Quién escribió \"El gran Gatsby\"?",
				new String[] { "F. Scott Fitzgerald", "Ernest Hemingway", "Mark Twain" }, "F. Scott Fitzgerald");

	}

	public Question getRandomQuestion() {
		Random random = new Random();
		int randomIndex = random.nextInt(questions.length);
		return questions[randomIndex];
	}
}
