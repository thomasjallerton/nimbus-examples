package com.nimbusframework.nimbusexampleidempotenttransaction;

import com.nimbusframework.nimbuscore.annotations.document.DocumentStore;
import com.nimbusframework.nimbuscore.annotations.persistent.Attribute;
import com.nimbusframework.nimbuscore.annotations.persistent.Key;
import java.util.UUID;

@DocumentStore
public class TransactionRequest {

  @Key
  private UUID transactionUid = UUID.randomUUID();

  @Attribute
  private String receiver;

  @Attribute
  private String sender;

  @Attribute
  private Float amount;

  public TransactionRequest() {}

  public TransactionRequest(String receiver, String sender, Float amount) {
    this.receiver = receiver;
    this.sender = sender;
    this.amount = amount;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public UUID getTransactionUid() {
    return transactionUid;
  }

  public void setTransactionUid(UUID transactionUid) {
    this.transactionUid = transactionUid;
  }

  public Float getAmount() {
    return amount;
  }

  public void setAmount(Float amount) {
    this.amount = amount;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }
}
