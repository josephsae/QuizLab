package org.quizlab.quizlab_game.question;

public class Question {
	private String statement;
	private String[] options;
	private String correctOption;

	public Question(String statement, String[] options, String correctOption) {
		this.statement = statement;
		this.options = options;
		this.correctOption = correctOption;
	}

	public String getStatement() {
		return statement;
	}

	public String[] getOptions() {
		return options;
	}

	public boolean esRespuestaCorrecta(String answer) {
		return correctOption.equals(answer);
	}
}