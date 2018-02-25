package com.example.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.model.Address;
import com.example.model.Person;
import com.example.model.hist.AddressHistory;
import com.example.repository.AddressHistoryRepository;
import com.example.repository.AddressRepository;
import com.example.service.AddressService;

/**
 * implementation of inteface {@link AddressService}.
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio Garcia</a>
 * @version
 * @sinse 25/02/2018 19:16:19
 */
@Service
public class AddressServiceImpl implements AddressService {

	/**
	 * Address Repository
	 */
	@Autowired
	private transient AddressRepository AddressRepository;

	@Autowired
	private transient AddressHistoryRepository AddressHistoryRepository;

	public Address find(Long code) {
		Optional<Address> optional = AddressRepository.findByCode(code);
		return optional.orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	@Override
	public List<Address> findAllRevision(Long code) {
		Sort sort = new Sort(Direction.DESC, "person.dtLastChange");
		AddressHistory filter = new AddressHistory();
		filter.setPerson(new Person(code));
		Example<AddressHistory> example = Example.of(filter);

		List<AddressHistory> history = AddressHistoryRepository.findAll(example, sort);
		return history.stream().map(hist -> {
			Address Address = new Address();
			BeanUtils.copyProperties(hist, Address);
			return Address;
		}).collect(Collectors.toList());
	}

	@Override
	public Address saveAddress(Address Address) {
		Address AddressEntity = AddressRepository.save(Address);
		return AddressEntity;
	}

	@Override
	public void deleteAddress(Long code) {
		Address Address = this.find(code);
		AddressRepository.delete(Address);
	}

	@Override
	public Address udpateAddress(Address Address) {
		Address AddressDefault = find(Address.getCode());
		BeanUtils.copyProperties(Address, AddressDefault, "code");
		return this.AddressRepository.save(AddressDefault);
	}
}
