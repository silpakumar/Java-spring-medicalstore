package com.example.demo.Model;


import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;




@Entity
@Table(name = "medicine")
public class Medicine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="medicine_id")
	private int id;
	

    
	@Column(name ="Mname")

	private String mname;

    
	@Column(name ="description")
	
	private String description;


	@Column(name ="price")
	
	private int price;

    
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "expdate")

	private Date expdate;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	

	public String getMname() {
		return mname;
	}


	public void setMname(String mname) {
		this.mname = mname;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public Date getExpdate() {
		return expdate;
	}


	public void setExpdate(Date expdate) {
		this.expdate = expdate;
	}
	
	
}