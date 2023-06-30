package org.quizlab.quizlab_game.question;

import java.util.Random;

public class RandomQuestionSelector {
	private Question[] questions;

	public RandomQuestionSelector() {
		questions = new Question[10];

		questions[0] = new Question("¿Cuál es el río más largo del mundo?",
				new String[] { "Nilo", "Amazonas", "Misisipi" }, "Amazonas");
		questions[1] = new Question("¿En qué año se descubrió América?", new String[] { "1492", "1776", "1812" },
				"1492");
		questions[2] = new Question("¿Cuál es el metal más abundante en la corteza terrestre?",
				new String[] { "Aluminio", "Hierro", "Oro" }, "Aluminio");
		questions[3] = new Question("¿Cuál es la capital de Australia?",
				new String[] { "Sídney", "Melbourne", "Canberra" }, "Canberra");
		questions[4] = new Question("¿Quién pintó la Mona Lisa?",
				new String[] { "Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh" }, "Leonardo da Vinci");
		questions[5] = new Question("¿Cuál es el idioma más hablado en el mundo?",
				new String[] { "Chino mandarín", "Español", "Inglés" }, "Chino mandarín");
		questions[6] = new Question("¿En qué año se celebraron los primeros Juegos Olímpicos modernos?",
				new String[] { "1896", "1908", "1924" }, "1896");
		questions[7] = new Question("¿Cuál es el planeta más grande del sistema solar?",
				new String[] { "Júpiter", "Saturno", "Urano" }, "Júpiter");
		questions[8] = new Question("¿Quién escribió la obra de Romeo y Julieta?",
				new String[] { "William Shakespeare", "Miguel de Cervantes", "Friedrich Nietzsche" },
				"William Shakespeare");
		questions[9] = new Question("¿Cuál es el símbolo químico del oro?", new String[] { "Au", "Ag", "Fe" }, "Au");

	}

	public Question getRandomQuestion() {
		Random random = new Random();
		int randomIndex = random.nextInt(questions.length);
		return questions[randomIndex];
	}
}
