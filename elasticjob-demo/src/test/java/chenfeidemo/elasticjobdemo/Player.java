package chenfeidemo.elasticjobdemo;

import java.util.List;

public class Player {


	private String id;

	private String name;

	private List<String>  cards;

	@Override
	public String toString() {
		return "Player{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", cards=" + cards +
				'}';
	}

	public Player(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCards() {
		return cards;
	}

	public void setCards(List<String> cards) {
		this.cards = cards;
	}
}
