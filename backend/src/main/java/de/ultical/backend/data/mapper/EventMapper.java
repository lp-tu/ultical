package de.ultical.backend.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import de.ultical.backend.model.Contact;
import de.ultical.backend.model.Event;
import de.ultical.backend.model.Team;
import de.ultical.backend.model.User;

public interface EventMapper extends BaseMapper<Event> {

    // INSERT
    @Override
    @Insert({
            "INSERT INTO EVENT (matchday_number, tournament_edition, start_date, end_date, local_organizer, info) VALUES",
            "(#{matchdayNumber, jdbcType=INTEGER},#{tournamentEdition.id, jdbcType=INTEGER},#{startDate, jdbcType=DATE},#{endDate, jdbcType=DATE},#{localOrganizer.id, jdbcType=INTEGER}, #{info, jdbcType=VARCHAR})" })
    @Options(keyProperty = "id", useGeneratedKeys = true)
    Integer insert(Event event);

    @Insert("INSERT INTO EVENT_ULTICAL_USERS (event, admin) VALUES (#{event.id},#{user.id})")
    public Integer insertAdmin(@Param("event") Event event, @Param("user") User user);

    // UPDATE
    @Override
    @Update({ "UPDATE EVENT SET version=version+1, tournament_edition=#{tournamentEdition.id},",
            "start_date=#{startDate}, end_date=#{endDate},",
            "local_organizer=#{localOrganizer.id, jdbcType=INTEGER}, info=#{info, jdbcType=VARCHAR}",
            "WHERE version=#{version} AND id=#{id}" })
    Integer update(Event entity);

    // DELETE
    @Override
    @Delete("DELETE FROM EVENT WHERE id=#{id}")
    void delete(Event entity);

    @Delete("DELETE FROM EVENT_ULTICAL_USERS WHERE event=#{event.id} AND admin=#{user.id}")
    public void deleteAdmin(@Param("event") Event event, @Param("user") User user);

    @Delete("DELETE FROM EVENT_ULTICAL_USERS WHERE event = #{event.id}")
    void removeAllAdmins(@Param("event") Team event);

    // SELECT
    @Override
    @Select("SELECT * FROM EVENT WHERE id = #{id}")
    @Results({ @Result(column = "matchday_number", property = "matchdayNumber"),
            @Result(column = "id", property = "id"), @Result(column = "version", property = "version"),
            @Result(column = "tournament_edition", property = "tournamentEdition", one = @One(select = "de.ultical.backend.data.mapper.TournamentEditionMapper.getForEvent") ),
            @Result(column = "id", property = "locations", many = @Many(select = "de.ultical.backend.data.mapper.LocationMapper.getForEvent") ),
            @Result(column = "start_date", property = "startDate"), @Result(column = "end_date", property = "endDate"),
            @Result(column = "info", property = "info"),
            @Result(column = "id", property = "admins", many = @Many(select = "de.ultical.backend.data.mapper.UserMapper.getAdminsForEvent") ),
            @Result(column = "id", property = "fees", many = @Many(select = "de.ultical.backend.data.mapper.FeeMapper.getForEvent") ),
            @Result(column = "local_organizer", property = "localOrganizer", one = @One(select = "de.ultical.backend.data.mapper.ContactMapper.get") , javaType = Contact.class) })
    Event get(int id);

    @Override
    @Select("SELECT * FROM EVENT")
    @Results({ @Result(column = "matchday_number", property = "matchdayNumber"),
            @Result(column = "id", property = "id"), @Result(column = "version", property = "version"),
            @Result(column = "tournament_edition", property = "tournamentEdition", one = @One(select = "de.ultical.backend.data.mapper.TournamentEditionMapper.getForEvent") ),
            @Result(column = "id", property = "locations", many = @Many(select = "de.ultical.backend.data.mapper.LocationMapper.getForEvent") ),
            @Result(column = "start_date", property = "startDate"), @Result(column = "end_date", property = "endDate"),
            @Result(column = "info", property = "info"),
            @Result(column = "id", property = "admins", many = @Many(select = "de.ultical.backend.data.mapper.UserMapper.getAdminsForEvent") ),
            @Result(column = "id", property = "fees", many = @Many(select = "de.ultical.backend.data.mapper.FeeMapper.getForEvent") ),
            @Result(column = "local_organizer", property = "localOrganizer", one = @One(select = "de.ultical.backend.data.mapper.ContactMapper.get") , javaType = Contact.class) })
    List<Event> getAll();

    @Select("SELECT * FROM EVENT WHERE tournament_edition=#{editionId}")
    @Results({ @Result(column = "matchday_number", property = "matchdayNumber"),
            @Result(column = "id", property = "id"), @Result(column = "version", property = "version"),
            @Result(column = "id", property = "locations", many = @Many(select = "de.ultical.backend.data.mapper.LocationMapper.getForEvent") ),
            @Result(column = "start_date", property = "startDate"), @Result(column = "end_date", property = "endDate"),
            @Result(column = "info", property = "info"),
            @Result(column = "id", property = "admins", many = @Many(select = "de.ultical.backend.data.mapper.UserMapper.getAdminsForEvent") ),
            @Result(column = "id", property = "fees", many = @Many(select = "de.ultical.backend.data.mapper.FeeMapper.getForEvent") ),
            @Result(column = "local_organizer", property = "localOrganizer", one = @One(select = "de.ultical.backend.data.mapper.ContactMapper.get") , javaType = Contact.class) })
    List<Event> getEventsForEdition(int editionId);

}
