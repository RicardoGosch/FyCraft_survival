package net.fycraft.plugins.vip.api.pagseguro.domain.direct;

import net.fycraft.plugins.vip.api.pagseguro.domain.Document;
import net.fycraft.plugins.vip.api.pagseguro.domain.Phone;
import net.fycraft.plugins.vip.api.pagseguro.helper.PagSeguroUtil;

/**
 * Represents the holder of the credit card payment
 */
public class Holder {

    /**
     * Holder name
     */
    private final String name;

    /**
     * Holder phone
     */
    private final Phone phone;

    /**
     * Holder document
     */
    private final Document document;

    /**
     * Holder birth date
     */
    private final String birthDate;

    /**
     * Initializes a new instance of the Holder class
     * 
     * @param name
     * @param phone
     * @param document
     * @param birthDate
     */
    public Holder(String name, Phone phone, Document document, String birthDate) {
        this.name = PagSeguroUtil.removeExtraSpaces(name);
        this.phone = phone;
        this.document = document;
        this.birthDate = birthDate;
    }

    /**
     * @return the holder name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the holder phone
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * @return the holder document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @return the holder birth date
     */
    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Holder[");
        sb.append("name=" + name);
        sb.append(",phone=" + phone);
        sb.append(",document=" + document);
        sb.append(",birthDate=" + birthDate);
        sb.append("]");
        return sb.toString();
    }

}
