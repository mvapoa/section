package br.com.sicredi.challenge.section.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SESSION")
public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agenda_id")
	private Agenda agenda;

	@OneToMany(mappedBy = "session", fetch = FetchType.EAGER)
	private Set<Vote> votes;

	@Column(name = "OPEN_TIME", nullable = false)
	private LocalDateTime openTime;

	@Column(name = "CLOSE_TIME", nullable = false)
	private LocalDateTime closeTime;

	@Column(name = "PROCESSED", nullable = false)
	private Boolean processed;

	public Session() {
		super();
	}

	public Session(Long id, Agenda agenda, Set<Vote> votes, LocalDateTime openTime, LocalDateTime closeTime,
			Boolean processed) {
		super();
		this.id = id;
		this.agenda = agenda;
		this.votes = votes;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.processed = processed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Set<Vote> getVotes() {
		return votes;
	}

	public void setVotes(Set<Vote> votes) {
		this.votes = votes;
	}

	public LocalDateTime getOpenTime() {
		return openTime;
	}

	public void setOpenTime(LocalDateTime openTime) {
		this.openTime = openTime;
	}

	public LocalDateTime getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(LocalDateTime closeTime) {
		this.closeTime = closeTime;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
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
		Session other = (Session) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", agenda=" + agenda + ", votes=" + votes + ", openTime=" + openTime
				+ ", closeTime=" + closeTime + ", processed=" + processed + "]";
	}

}
