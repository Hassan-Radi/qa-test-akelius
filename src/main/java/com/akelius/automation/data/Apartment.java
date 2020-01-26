package com.akelius.automation.data;

public class Apartment {

  private String title;
  private String address;
  private String rooms;
  private int size;
  private String floor;
  private int rent;
  private String availableFrom;

  public Apartment(
      String title,
      String address,
      String rooms,
      int size,
      String floor,
      int rent,
      String availableFrom) {
    super();
    this.title = title;
    this.address = address;
    this.rooms = rooms;
    this.size = size;
    this.floor = floor;
    this.rent = rent;
    this.availableFrom = availableFrom;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((availableFrom == null) ? 0 : availableFrom.hashCode());
    result = prime * result + ((floor == null) ? 0 : floor.hashCode());
    result = prime * result + rent;
    result = prime * result + ((rooms == null) ? 0 : rooms.hashCode());
    result = prime * result + size;
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Apartment other = (Apartment) obj;
    if (address == null) {
      if (other.address != null) return false;
    } else if (!address.equals(other.address)) return false;
    if (availableFrom == null) {
      if (other.availableFrom != null) return false;
    } else if (!availableFrom.equals(other.availableFrom)) return false;
    if (floor == null) {
      if (other.floor != null) return false;
    } else if (!floor.equals(other.floor)) return false;
    if (rent != other.rent) return false;
    if (rooms == null) {
      if (other.rooms != null) return false;
    } else if (!rooms.equals(other.rooms)) return false;
    if (size != other.size) return false;
    if (title == null) {
      if (other.title != null) return false;
    } else if (!title.equals(other.title)) return false;
    return true;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getRooms() {
    return rooms;
  }

  public void setRooms(String rooms) {
    this.rooms = rooms;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public int getRent() {
    return rent;
  }

  public void setRent(int rent) {
    this.rent = rent;
  }

  public String getAvailableFrom() {
    return availableFrom;
  }

  public void setAvailableFrom(String availableFrom) {
    this.availableFrom = availableFrom;
  }
}
