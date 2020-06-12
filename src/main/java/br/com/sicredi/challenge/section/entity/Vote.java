package br.com.sicredi.challenge.section.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "VOTE")
public class Vote implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "associate_id")
	private Associate associate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "session_id")
	private Session session;

	@Column(name = "VOTE", nullable = false)
	private Boolean vote;

	@Column(name = "TIME_VOTE", nullable = false)
	private LocalDateTime timeVote;

	public Vote() {
		super();
	}

	public Vote(Long id, Associate associate, Session session, Boolean vote, LocalDateTime timeVote) {
		super();
		this.id = id;
		this.associate = associate;
		this.session = session;
		this.vote = vote;
		this.timeVote = timeVote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Associate getAssociate() {
		return associate;
	}

	public void setAssociate(Associate associate) {
		this.associate = associate;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Boolean getVote() {
		return vote;
	}

	public void setVote(Boolean vote) {
		this.vote = vote;
	}

	public LocalDateTime getTimeVote() {
		return timeVote;
	}

	public void setTimeVote(LocalDateTime timeVote) {
		this.timeVote = timeVote;
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
		Vote other = (Vote) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vote [id=" + id + ", associate=" + associate + ", session=" + session + ", vote=" + vote + ", timeVote="
				+ timeVote + "]";
	}

}
