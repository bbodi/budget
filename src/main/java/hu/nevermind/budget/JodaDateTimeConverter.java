package hu.nevermind.budget;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;
import org.joda.time.LocalDate;

public class JodaDateTimeConverter implements Converter {

	private static final long serialVersionUID = 1L;

	@Override
	public Object convertDataValueToObjectValue(final Object dataValue, final Session session) {
		return dataValue == null ? null : new LocalDate(dataValue);
	}

	@Override
	public Object convertObjectValueToDataValue(final Object objectValue, final Session session) {
		return objectValue == null ? null : new java.sql.Date(((LocalDate)objectValue).toDate().getTime());
	}

	@Override
	public void initialize(final DatabaseMapping mapping, final Session session) {
	}

	@Override
	public boolean isMutable() {
		return false;
	}

}