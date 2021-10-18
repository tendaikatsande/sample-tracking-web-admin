package com.sampletracking.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Shipment.
 */
@Entity
@Table(name = "shipment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "description")
    private String description;

    @Column(name = "client_id")
    private String clientId;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "samples")
    private String samples;

    @Column(name = "status")
    private String status;

    @Column(name = "date_created")
    private String dateCreated;

    @Column(name = "date_modified")
    private String dateModified;

    @Column(name = "rider_id")
    private String riderId;

    @Column(name = "rider_name")
    private String riderName;

    @Column(name = "destination")
    private String destination;

    @Column(name = "cluster_client_id")
    private String clusterClientId;

    @Column(name = "temperature_origin")
    private String temperatureOrigin;

    @Column(name = "temperature_destination")
    private String temperatureDestination;

    @Column(name = "is_modified_by_hub")
    private Boolean isModifiedByHub;

    @Column(name = "is_modified_by_facility")
    private Boolean isModifiedByFacility;

    @Column(name = "is_modified_by_laboratory")
    private Boolean isModifiedByLaboratory;

    @Column(name = "is_modified_by_courier")
    private Boolean isModifiedByCourier;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Shipment id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return this.appId;
    }

    public Shipment appId(String appId) {
        this.setAppId(appId);
        return this;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDescription() {
        return this.description;
    }

    public Shipment description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientId() {
        return this.clientId;
    }

    public Shipment clientId(String clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSamples() {
        return this.samples;
    }

    public Shipment samples(String samples) {
        this.setSamples(samples);
        return this;
    }

    public void setSamples(String samples) {
        this.samples = samples;
    }

    public String getStatus() {
        return this.status;
    }

    public Shipment status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateCreated() {
        return this.dateCreated;
    }

    public Shipment dateCreated(String dateCreated) {
        this.setDateCreated(dateCreated);
        return this;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return this.dateModified;
    }

    public Shipment dateModified(String dateModified) {
        this.setDateModified(dateModified);
        return this;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getRiderId() {
        return this.riderId;
    }

    public Shipment riderId(String riderId) {
        this.setRiderId(riderId);
        return this;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getRiderName() {
        return this.riderName;
    }

    public Shipment riderName(String riderName) {
        this.setRiderName(riderName);
        return this;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getDestination() {
        return this.destination;
    }

    public Shipment destination(String destination) {
        this.setDestination(destination);
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getClusterClientId() {
        return this.clusterClientId;
    }

    public Shipment clusterClientId(String clusterClientId) {
        this.setClusterClientId(clusterClientId);
        return this;
    }

    public void setClusterClientId(String clusterClientId) {
        this.clusterClientId = clusterClientId;
    }

    public String getTemperatureOrigin() {
        return this.temperatureOrigin;
    }

    public Shipment temperatureOrigin(String temperatureOrigin) {
        this.setTemperatureOrigin(temperatureOrigin);
        return this;
    }

    public void setTemperatureOrigin(String temperatureOrigin) {
        this.temperatureOrigin = temperatureOrigin;
    }

    public String getTemperatureDestination() {
        return this.temperatureDestination;
    }

    public Shipment temperatureDestination(String temperatureDestination) {
        this.setTemperatureDestination(temperatureDestination);
        return this;
    }

    public void setTemperatureDestination(String temperatureDestination) {
        this.temperatureDestination = temperatureDestination;
    }

    public Boolean getIsModifiedByHub() {
        return this.isModifiedByHub;
    }

    public Shipment isModifiedByHub(Boolean isModifiedByHub) {
        this.setIsModifiedByHub(isModifiedByHub);
        return this;
    }

    public void setIsModifiedByHub(Boolean isModifiedByHub) {
        this.isModifiedByHub = isModifiedByHub;
    }

    public Boolean getIsModifiedByFacility() {
        return this.isModifiedByFacility;
    }

    public Shipment isModifiedByFacility(Boolean isModifiedByFacility) {
        this.setIsModifiedByFacility(isModifiedByFacility);
        return this;
    }

    public void setIsModifiedByFacility(Boolean isModifiedByFacility) {
        this.isModifiedByFacility = isModifiedByFacility;
    }

    public Boolean getIsModifiedByLaboratory() {
        return this.isModifiedByLaboratory;
    }

    public Shipment isModifiedByLaboratory(Boolean isModifiedByLaboratory) {
        this.setIsModifiedByLaboratory(isModifiedByLaboratory);
        return this;
    }

    public void setIsModifiedByLaboratory(Boolean isModifiedByLaboratory) {
        this.isModifiedByLaboratory = isModifiedByLaboratory;
    }

    public Boolean getIsModifiedByCourier() {
        return this.isModifiedByCourier;
    }

    public Shipment isModifiedByCourier(Boolean isModifiedByCourier) {
        this.setIsModifiedByCourier(isModifiedByCourier);
        return this;
    }

    public void setIsModifiedByCourier(Boolean isModifiedByCourier) {
        this.isModifiedByCourier = isModifiedByCourier;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Shipment createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Shipment createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Shipment lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Shipment lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shipment)) {
            return false;
        }
        return id != null && id.equals(((Shipment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Shipment{" +
            "id=" + getId() +
            ", appId='" + getAppId() + "'" +
            ", description='" + getDescription() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", samples='" + getSamples() + "'" +
            ", status='" + getStatus() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            ", riderId='" + getRiderId() + "'" +
            ", riderName='" + getRiderName() + "'" +
            ", destination='" + getDestination() + "'" +
            ", clusterClientId='" + getClusterClientId() + "'" +
            ", temperatureOrigin='" + getTemperatureOrigin() + "'" +
            ", temperatureDestination='" + getTemperatureDestination() + "'" +
            ", isModifiedByHub='" + getIsModifiedByHub() + "'" +
            ", isModifiedByFacility='" + getIsModifiedByFacility() + "'" +
            ", isModifiedByLaboratory='" + getIsModifiedByLaboratory() + "'" +
            ", isModifiedByCourier='" + getIsModifiedByCourier() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
