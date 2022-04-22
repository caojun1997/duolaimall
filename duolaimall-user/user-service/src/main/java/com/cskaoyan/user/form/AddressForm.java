package com.cskaoyan.user.form;

import lombok.Data;


@Data
public class AddressForm {

	private Long addressId;

	private String userName;

	private String tel;

	private String streetName;

	private boolean _Default;
}
