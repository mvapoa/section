package br.com.sicredi.challenge.section.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RESULT")
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "session_id")
	private Session session;

	@Column(name = "WINNER", nullable = false)
	private Boolean winner;

	@Column(name = "TOTAL_TRUE", nullable = false)
	private Integer totalTrue;

	@Column(name = "TOTAL_FALSE", nullable = false)
	private Integer totalFalse;

	public Result() {
		super();
	}

	public Result(Long id, Session session, Boolean winner, Integer totalTrue, Integer totalFalse) {
		super();
		this.id = id;
		this.session = session;
		this.winner = winner;
		this.totalTrue = totalTrue;
		this.totalFalse = totalFalse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

	public Integer getTotalTrue() {
		return totalTrue;
	}

	public void setTotalTrue(Integer totalTrue) {
		this.totalTrue = totalTrue;
	}

	public Integer getTotalFalse() {
		return totalFalse;
	}

	public void setTotalFalse(Integer totalFalse) {
		this.totalFalse = totalFalse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Result other = (Result) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Result [id=" + id + ", session=" + session + ", winner=" + winner + ", totalTrue=" + totalTrue
				+ ", totalFalse=" + totalFalse + "]";
	}

}
