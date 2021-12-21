package com.capg.cba.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.cba.entities.TripBooking;
import com.capg.cba.exception.TripBookingNotFoundException;
//import com.capg.cba.exception.CabNotFoundException;
//import com.capg.cba.exception.DriverNotFoundException;
import com.capg.cba.services.ITripBookingServiceI;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value="TripBooking")
@CrossOrigin("http://localhost:4200")
@Api(value="ITripBookingController")
public class ITripBookingController {
	@Autowired 
	ITripBookingServiceI itripbookingservicei;
	
	@PostMapping("/insert")
	public ResponseEntity<Boolean> insert(@RequestBody TripBooking pro) throws TripBookingNotFoundException
	{
		if(pro.getTripBookingId()==0)
			throw new TripBookingNotFoundException("Oops!!No Driver found for given Id");
		itripbookingservicei.insertTripBooking(pro);
		
		ResponseEntity<Boolean> responseEntity = new ResponseEntity<Boolean>(true, HttpStatus.OK);
		System.out.println("response entity=" + responseEntity);
		return responseEntity;
	}
	
	@GetMapping("/viewall/{id}")  //localhost:9090/product/getall
	public ResponseEntity<List<TripBooking>> viewall(@PathVariable int id) throws TripBookingNotFoundException
	{
		List<TripBooking> list=itripbookingservicei.ViewAllTripsCustomer(id);
        if(list.isEmpty())
 		throw new TripBookingNotFoundException("Oops!!The List is Empty");

		return new ResponseEntity<List<TripBooking>>(list,new HttpHeaders(),HttpStatus.OK);
	}
	
	@PutMapping("/update")  //localhost:9090/product/update
	public ResponseEntity<TripBooking> updateProduct(@RequestBody TripBooking booking) throws TripBookingNotFoundException
	{
		booking=itripbookingservicei.updateTripBooking(booking);
		if(booking.getTripBookingId()==0)
			throw new TripBookingNotFoundException("Oops!!No Driver found for given Id");

		return new ResponseEntity<TripBooking>(booking,new HttpHeaders(),HttpStatus.OK);
	}
	
	@DeleteMapping(path="/delete/{id}") //localhost:9090/delete/{empId}
	public ResponseEntity<String> deleteProduct(@PathVariable int id)
	{
		itripbookingservicei.deleteTripBooking(id);
		return new ResponseEntity<String>("Record TripBooking Deleted",HttpStatus.OK);
		
	}
	@GetMapping("/viewbill/{id}")
	public float calculate(@PathVariable int id) throws TripBookingNotFoundException

	{
		if(id==0)
			throw new TripBookingNotFoundException("Oops!!No Driver found for given Id");


		return itripbookingservicei.calculateBill(id);
	}
}