package com.sampletracking.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sample.
 */
@Entity
@Table(name = "sample")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sample implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "client_sample_id")
    private String clientSampleId;

    @Column(name = "client_patient_id")
    private String clientPatientId;

    @Column(name = "lab_id")
    private String labId;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "sample_type")
    private String sampleType;

    @Column(name = "test_id")
    private String testId;

    @Column(name = "date_collected")
    private Instant dateCollected;

    @Column(name = "collected_by")
    private String collectedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "synced")
    private Boolean synced;

    @Column(name = "date_synced")
    private Instant dateSynced;

    @Column(name = "lab_reference_id")
    private String labReferenceId;

    @Column(name = "location")
    private String location;

    @Column(name = "result")
    private String result;

    @Column(name = "result_received_by")
    private String resultReceivedBy;

    @Column(name = "shipment_id")
    private String shipmentId;

    @Column(name = "client_contact")
    private String clientContact;

    @Column(name = "temperature_at_hub")
    private String temperatureAtHub;

    @Column(name = "temperature_at_lab")
    private String temperatureAtLab;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private Instant modifiedBy;

    @Column(name = "date_created")
    private String dateCreated;

    @Column(name = "date_modified")
    private Instant dateModified;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Sample id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return this.appId;
    }

    public Sample appId(String appId) {
        this.setAppId(appId);
        return this;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getClientSampleId() {
        return this.clientSampleId;
    }

    public Sample clientSampleId(String clientSampleId) {
        this.setClientSampleId(clientSampleId);
        return this;
    }

    public void setClientSampleId(String clientSampleId) {
        this.clientSampleId = clientSampleId;
    }

    public String getClientPatientId() {
        return this.clientPatientId;
    }

    public Sample clientPatientId(String clientPatientId) {
        this.setClientPatientId(clientPatientId);
        return this;
    }

    public void setClientPatientId(String clientPatientId) {
        this.clientPatientId = clientPatientId;
    }

    public String getLabId() {
        return this.labId;
    }

    public Sample labId(String labId) {
        this.setLabId(labId);
        return this;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getClientId() {
        return this.clientId;
    }

    public Sample clientId(String clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSampleType() {
        return this.sampleType;
    }

    public Sample sampleType(String sampleType) {
        this.setSampleType(sampleType);
        return this;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getTestId() {
        return this.testId;
    }

    public Sample testId(String testId) {
        this.setTestId(testId);
        return this;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public Instant getDateCollected() {
        return this.dateCollected;
    }

    public Sample dateCollected(Instant dateCollected) {
        this.setDateCollected(dateCollected);
        return this;
    }

    public void setDateCollected(Instant dateCollected) {
        this.dateCollected = dateCollected;
    }

    public String getCollectedBy() {
        return this.collectedBy;
    }

    public Sample collectedBy(String collectedBy) {
        this.setCollectedBy(collectedBy);
        return this;
    }

    public void setCollectedBy(String collectedBy) {
        this.collectedBy = collectedBy;
    }

    public String getStatus() {
        return this.status;
    }

    public Sample status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return this.comment;
    }

    public Sample comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getSynced() {
        return this.synced;
    }

    public Sample synced(Boolean synced) {
        this.setSynced(synced);
        return this;
    }

    public void setSynced(Boolean synced) {
        this.synced = synced;
    }

    public Instant getDateSynced() {
        return this.dateSynced;
    }

    public Sample dateSynced(Instant dateSynced) {
        this.setDateSynced(dateSynced);
        return this;
    }

    public void setDateSynced(Instant dateSynced) {
        this.dateSynced = dateSynced;
    }

    public String getLabReferenceId() {
        return this.labReferenceId;
    }

    public Sample labReferenceId(String labReferenceId) {
        this.setLabReferenceId(labReferenceId);
        return this;
    }

    public void setLabReferenceId(String labReferenceId) {
        this.labReferenceId = labReferenceId;
    }

    public String getLocation() {
        return this.location;
    }

    public Sample location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getResult() {
        return this.result;
    }

    public Sample result(String result) {
        this.setResult(result);
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultReceivedBy() {
        return this.resultReceivedBy;
    }

    public Sample resultReceivedBy(String resultReceivedBy) {
        this.setResultReceivedBy(resultReceivedBy);
        return this;
    }

    public void setResultReceivedBy(String resultReceivedBy) {
        this.resultReceivedBy = resultReceivedBy;
    }

    public String getShipmentId() {
        return this.shipmentId;
    }

    public Sample shipmentId(String shipmentId) {
        this.setShipmentId(shipmentId);
        return this;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getClientContact() {
        return this.clientContact;
    }

    public Sample clientContact(String clientContact) {
        this.setClientContact(clientContact);
        return this;
    }

    public void setClientContact(String clientContact) {
        this.clientContact = clientContact;
    }

    public String getTemperatureAtHub() {
        return this.temperatureAtHub;
    }

    public Sample temperatureAtHub(String temperatureAtHub) {
        this.setTemperatureAtHub(temperatureAtHub);
        return this;
    }

    public void setTemperatureAtHub(String temperatureAtHub) {
        this.temperatureAtHub = temperatureAtHub;
    }

    public String getTemperatureAtLab() {
        return this.temperatureAtLab;
    }

    public Sample temperatureAtLab(String temperatureAtLab) {
        this.setTemperatureAtLab(temperatureAtLab);
        return this;
    }

    public void setTemperatureAtLab(String temperatureAtLab) {
        this.temperatureAtLab = temperatureAtLab;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Sample createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getModifiedBy() {
        return this.modifiedBy;
    }

    public Sample modifiedBy(Instant modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(Instant modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getDateCreated() {
        return this.dateCreated;
    }

    public Sample dateCreated(String dateCreated) {
        this.setDateCreated(dateCreated);
        return this;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateModified() {
        return this.dateModified;
    }

    public Sample dateModified(Instant dateModified) {
        this.setDateModified(dateModified);
        return this;
    }

    public void setDateModified(Instant dateModified) {
        this.dateModified = dateModified;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sample)) {
            return false;
        }
        return id != null && id.equals(((Sample) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sample{" +
            "id=" + getId() +
            ", appId='" + getAppId() + "'" +
            ", clientSampleId='" + getClientSampleId() + "'" +
            ", clientPatientId='" + getClientPatientId() + "'" +
            ", labId='" + getLabId() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", sampleType='" + getSampleType() + "'" +
            ", testId='" + getTestId() + "'" +
            ", dateCollected='" + getDateCollected() + "'" +
            ", collectedBy='" + getCollectedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", comment='" + getComment() + "'" +
            ", synced='" + getSynced() + "'" +
            ", dateSynced='" + getDateSynced() + "'" +
            ", labReferenceId='" + getLabReferenceId() + "'" +
            ", location='" + getLocation() + "'" +
            ", result='" + getResult() + "'" +
            ", resultReceivedBy='" + getResultReceivedBy() + "'" +
            ", shipmentId='" + getShipmentId() + "'" +
            ", clientContact='" + getClientContact() + "'" +
            ", temperatureAtHub='" + getTemperatureAtHub() + "'" +
            ", temperatureAtLab='" + getTemperatureAtLab() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            "}";
    }
}