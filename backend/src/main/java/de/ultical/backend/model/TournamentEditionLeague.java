package de.ultical.backend.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import de.ultical.backend.data.mapper.BaseMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TournamentEditionLeague extends TournamentEdition {

	@JsonManagedReference
	private Set<Event> events;
	private String alternativeMatchdayName;

	@Override
	public Class<? extends BaseMapper<? extends Identifiable>> getMapper() {
		// TODO Auto-generated method stub
		return null;
	}
}
