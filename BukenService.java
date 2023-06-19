package com.example.demo.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Buken;
import com.example.demo.entity.Message;
import com.example.demo.entity.Okiniiri;
import com.example.demo.entity.Shinsa;
import com.example.demo.entity.Tintai;
import com.example.demo.entity.User;
import com.example.demo.obj.BukenDto;
import com.example.demo.obj.MessageDto;
import com.example.demo.obj.ShinsaDto;
import com.example.demo.obj.TintaiDto;
import com.example.demo.obj.UserDto;
import com.example.demo.repository.BukenRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.OkiniiriRepository;
import com.example.demo.repository.ShinsaRepository;
import com.example.demo.repository.TintaiRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class BukenService {

	@Autowired
	private BukenRepository bukenRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private ShinsaRepository shinsaRepository;
	@Autowired
	private TintaiRepository tintaiRepository;
	@Autowired
	private OkiniiriRepository okiniiriRepository;

	// 物件公開一覧
	public List<BukenDto> searchAll() {
		List<Buken> bukenList = bukenRepository.findAll(Sort.by(Order.asc("propertyId")));
		List<BukenDto> bukenDtoList = new ArrayList<>();

		for (Buken buken : bukenList) {
			BukenDto bukenDto = convertToDto(buken);
			bukenDtoList.add(bukenDto);
		}

		return bukenDtoList;
	}

	private BukenDto convertToDto(Buken buken) {
		Long id = buken.getPropertyId();
		String name = buken.getPropertyName();
		String address = buken.getAddress();
		String propertyType = buken.getPropertyType();
		Date period = buken.getPeriod();
		String propertyArea = buken.getPropertyArea();
		String price = buken.getPrice();
		String syozokuCompanyId = buken.getSyozokuCompanyId();
		String status = buken.getStatus();
		String tyuusyajyou = buken.getTyuusyajyou();
		String ekikaraminute = buken.getEkikaraminute();
		String koutiku = buken.getKoutiku();
		int kaisou = buken.getKaisou();
		String imagePath = buken.getImagePath();
		return new BukenDto(id, name, address, propertyType, period, propertyArea, price, syozokuCompanyId, status,
				tyuusyajyou, ekikaraminute, koutiku, kaisou, imagePath);
	}

	// 物件非公開一覧

	public List<BukenDto> searchBukenHikoukei() {
		List<Buken> bukenList = bukenRepository.findByStatusContainingOrderByPropertyId("なし");

		List<BukenDto> bukenDtoList = new ArrayList<>();

		for (Buken buken : bukenList) {
			BukenDto bukenDto = convertToDtohikoukei(buken);
			bukenDtoList.add(bukenDto);
		}

		return bukenDtoList;
	}

	private BukenDto convertToDtohikoukei(Buken buken) {
		Long id = buken.getPropertyId();
		String name = buken.getPropertyName();
		String address = buken.getAddress();
		String propertyType = buken.getPropertyType();
		Date period = buken.getPeriod();
		String propertyArea = buken.getPropertyArea();
		String price = buken.getPrice();
		String syozokuCompanyId = buken.getSyozokuCompanyId();
		String status = buken.getStatus();
		String tyuusyajyou = buken.getTyuusyajyou();
		String ekikaraminute = buken.getEkikaraminute();
		String koutiku = buken.getKoutiku();
		int kaisou = buken.getKaisou();
		String imagePath = buken.getImagePath();
		return new BukenDto(id, name, address, propertyType, period, propertyArea, price, syozokuCompanyId, status,
				tyuusyajyou, ekikaraminute, koutiku, kaisou, imagePath);
	}

	// ユーザ一覧
	public List<UserDto> searchAllUser() {
		List<User> userList = userRepository.findAll(Sort.by(Order.asc("id")));
		List<UserDto> userDtoList = new ArrayList<>();

		for (User user : userList) {
			UserDto userDto = convertToDtoUser(user);
			userDtoList.add(userDto);
		}

		return userDtoList;
	}

	private UserDto convertToDtoUser(User user) {
		long id = user.getId();
		String userName = user.getUserName();
		String password = user.getPassword();
		String status = user.getStatus();

		return new UserDto(id, userName, password, status);
	}

	// 賃貸一覧
	public List<TintaiDto> searchAllTintai() {
		List<Tintai> tintaiList = tintaiRepository.findAll(Sort.by(Order.asc("id")));
		List<TintaiDto> tintaiDtoList = new ArrayList<>();

		for (Tintai tintai : tintaiList) {
			TintaiDto tintaiDto = convertToDtoTintai(tintai);
			tintaiDtoList.add(tintaiDto);
		}

		return tintaiDtoList;
	}

	private TintaiDto convertToDtoTintai(Tintai tintai) {
		long id = tintai.getId();
		String okyaku = tintai.getOkyaku();
		Long tintaiBukenId = tintai.getTintaiBukenId();
		String shinsasyaA = tintai.getShinsasyaA();
		String shinsasyaB = tintai.getShinsasyaB();
		return new TintaiDto(id, okyaku, tintaiBukenId, shinsasyaA, shinsasyaB);
	}

	// message一覧
	public List<MessageDto> searchAllMessage() {
		List<Message> messageList = messageRepository.findAll(Sort.by(Order.asc("id")));
		List<MessageDto> messageDtoList = new ArrayList<>();

		for (Message message : messageList) {
			MessageDto messageDto = convertToDtoMessage(message);
			messageDtoList.add(messageDto);
		}

		return messageDtoList;
	}

	private MessageDto convertToDtoMessage(Message message) {
		long id = message.getId();
		String messages = message.getMessages();
		String status = message.getStatus();

		return new MessageDto(id, messages, status);
	}

	// 審査一覧
	public List<ShinsaDto> searchAllShinsa() {
		List<Shinsa> shinsaList = shinsaRepository.findAll(Sort.by(Order.asc("id")));
		List<ShinsaDto> shinsaDtoList = new ArrayList<>();

		for (Shinsa shinsa : shinsaList) {
			ShinsaDto shinsaDto = convertToDtoShinsa(shinsa);
			shinsaDtoList.add(shinsaDto);
		}

		return shinsaDtoList;
	}

	private ShinsaDto convertToDtoShinsa(Shinsa shinsa) {
		long id = shinsa.getId();
		long bukenId = shinsa.getBukenId();
		String shinsaA = shinsa.getShinsaA();
		String shinsaB = shinsa.getShinsaB();
		String status = shinsa.getStatus();

		return new ShinsaDto(id, bukenId, shinsaA, shinsaB, status, null, null);
	}

	// 物件新規の保存
	public Buken saveBuken(Buken buken) {
		// BukenRepository を使って、DBに保存する
		return bukenRepository.save(buken);
	}

	// User新規の保存
	public User saveUser(User user) {
		// UserRepository を使って、DBに保存する
		return userRepository.save(user);
	}

	// message新規の保存
	public Message saveMessage(Message message) {
		// messageRepository を使って、DBに保存する
		return messageRepository.save(message);
	}

	// 審査新規の保存
	public Shinsa saveShinsa(Shinsa shinsa) {
		// ShinsaRepository を使って、DBに保存する
		return shinsaRepository.save(shinsa);
	}

	// 検索

	public List<BukenDto> searchById(Long propertyId) {
		List<Buken> bukenList;

		if (propertyId != null) {
			bukenList = bukenRepository.findByPropertyId(propertyId);
		} else {
			bukenList = bukenRepository.findAll(Sort.by(Order.asc("propertyId")));
		}

		List<BukenDto> bukenDtoList = new ArrayList<>();
		for (Buken buken : bukenList) {
			BukenDto bukenDto = convertToDto(buken);
			bukenDtoList.add(bukenDto);
		}

		return bukenDtoList;
	}

	// 期間検索
	public List<Buken> searchByPeriod(Date fromdate, Date todate) {
		return bukenRepository.findByPeriodBetweenOrderByPeriod(fromdate, todate);
	}

	// 検索
	// 构造函数注入BukenRepository
	public BukenService(BukenRepository bukenRepository) {
		this.bukenRepository = bukenRepository;
	}

	public List<Buken> searchByUidValue(Long propertyId) {
		return bukenRepository.findByPropertyId(propertyId);
	}
	
	//お客さん検索
//	public List<Buken> okyakuSearch(Long propertyId, Integer rentMin, Integer rentMax, Integer areaMin, Integer areaMax, String[] layouts, String minutesToStation, String age, String[] structures, String tyuusyajyou, Integer kaisou) {
//	    Specification<Buken> spec = Specification.where(null);
//	    
//	    spec = spec.and((root, query, builder) -> builder.equal(root.get("uid"), propertyId));
//
//	    if (rentMin != null) {
//	        spec = spec.and((root, query, builder) -> builder.ge(root.get("rent"), rentMin));
//	    }
//	    if (rentMax != null) {
//	        spec = spec.and((root, query, builder) -> builder.le(root.get("rent"), rentMax));
//	    }
//
//	    if (areaMin != null) {
//	        spec = spec.and((root, query, builder) -> builder.ge(root.get("area"), areaMin));
//	    }
//	    if (areaMax != null) {
//	        spec = spec.and((root, query, builder) -> builder.le(root.get("area"), areaMax));
//	    }
//
//	    // 間取り条件
//	    if (layouts != null && layouts.length > 0) {
//	        spec = spec.and((root, query, builder) -> root.get("layout").in(layouts));
//	    }
//
//	    // 駅条件
//	    if (minutesToStation != null) {
//	        spec = spec.and((root, query, builder) -> builder.equal(root.get("minutesToStation"), minutesToStation));
//	    }
//
//	    // 築年数条件
//	    if (age != null) {
//	        spec = spec.and((root, query, builder) -> builder.equal(root.get("age"), age));
//	    }
//
//	    // 建物構造条件
//	    if (structures != null && structures.length > 0) {
//	        spec = spec.and((root, query, builder) -> root.get("structures").in(structures));
//	    }
//
//	    // 
//	    if (tyuusyajyou != null) {
//	        spec = spec.and((root, query, builder) -> builder.equal(root.get("otherCondition1"), tyuusyajyou));
//	    }
//	    if (kaisou != null) {
//	        spec = spec.and((root, query, builder) -> builder.equal(root.get("otherCondition2"), kaisou));
//	    }
//
//	    
//	    List<Buken> bukenList = bukenRepository.findAll(spec);
//	    
//	    return bukenList;
//	}
	
	//お客さん検索
//	public List<Buken> okyakuSearch(Long propertyId, Integer rentMin, Integer rentMax, Integer areaMin, Integer areaMax, String[] layouts, String minutesToStation, String age, String[] structures, String tyuusyajyou, Integer kaisou) {
//		return bukenRepository.findByPropertyIdAndPriceBetweenAndpropertyAreaBetweenAndLayoutInAndMinutesToStationAndAgeAndStructureInAndTyuusyajyouAndKaisou(propertyId,rentMin,rentMax,areaMin,areaMax,layouts,minutesToStation,age,structures,tyuusyajyou,kaisou);
//	}
//	public List<Buken> okyakuSearch(String rentMin, String rentMax, String areaMin, String areaMax) {
//		return bukenRepository.findByPriceBetweenAndPropertyAreaBetween(rentMin,rentMax,areaMin,areaMax);
//	}
	public List<Buken> okyakuSearch(String layouts) {
		return bukenRepository.findByPropertyType(layouts);
	}


	// 賃貸検索
	public List<Tintai> tintai(String name) {
		return tintaiRepository.findByOkyaku(name);
	}

	// 物件削除
	public void deleteBuken(Long id) {
		bukenRepository.deleteById(id);
	}

	// ユーザ削除
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	// 物件編集
	public void updateBuken(BukenDto bukenDto) {
		Long propertyId = bukenDto.getPropertyId();
		Optional<Buken> optionalBuken = bukenRepository.findById(propertyId);
		if (optionalBuken.isPresent()) {
			Buken buken = optionalBuken.get();
			buken.setPropertyName(bukenDto.getPropertyName());
			buken.setAddress(bukenDto.getAddress());
			buken.setPropertyType(bukenDto.getPropertyType());
			buken.setPeriod(bukenDto.getPeriod());
			buken.setPropertyArea(bukenDto.getPropertyArea());
			buken.setPrice(bukenDto.getPrice());
			buken.setSyozokuCompanyId(bukenDto.getSyozokuCompanyId());
			buken.setStatus(bukenDto.getStatus());
			buken.setTyuusyajyou(bukenDto.getTyuusyajyou());
			buken.setEkikaraminute(bukenDto.getEkikaraminute());
			buken.setKoutiku(bukenDto.getKoutiku());
			buken.setKaisou(bukenDto.getKaisou());
			// buken.setImagePath(bukenDto.getImagePath());
			bukenRepository.save(buken);
		} else {
			throw new NotFoundException("Buken not found with ID: " + propertyId);
		}
	}

	// 物件注文
	public void updateBukenStatus(BukenDto bukenDto) {
		Long propertyId = bukenDto.getPropertyId();
		Optional<Buken> optionalBuken = bukenRepository.findById(propertyId);
		if (optionalBuken.isPresent()) {
			Buken buken = optionalBuken.get();
			buken.setStatus("あり");
			bukenRepository.save(buken);
		} else {
			throw new NotFoundException("Buken not found with ID: " + propertyId);
		}
	}

	// message新規(注文)
	public void insertMessage(HttpSession session, Long propertyId) {
		Message message = new Message();

		String userName = (String) session.getAttribute("userName");
		String messages = userName + "様、物件 " + propertyId + " を注文しました。";
		session.setAttribute("propertyId", propertyId);
		Long propertyId1 = (Long) session.getAttribute("propertyId");
		System.out.println("Property ID: " + propertyId1);

		message.setMessages(messages);
		message.setStatus("未確認");

		messageRepository.save(message);
	}

	// 賃貸の新規
	@Transactional
	public void insertTintai(Long bukenId) {
		List<Shinsa> shinsaList = shinsaRepository.findAllByBukenId(bukenId);
		if (!shinsaList.isEmpty()) {
			for (Shinsa shinsa : shinsaList) {
				Tintai tintai = new Tintai();

				tintai.setTintaiBukenId(shinsa.getBukenId());
				tintai.setOkyaku("固定客户");
				tintai.setShinsasyaA(shinsa.getShinsaA());
				tintai.setShinsasyaB(shinsa.getShinsaB());

				tintaiRepository.save(tintai);

				tintaiRepository.updateTintaiFields(tintai.getTintaiBukenId(), tintai.getShinsasyaA(),
						tintai.getShinsasyaB());
			}
		}
	}

	// お気に入りの新規
	public void insertOkiniiri(Long bukenId, HttpSession session) {
		Optional<Buken> bukenOptional = bukenRepository.findById(bukenId);
		String userName = (String) session.getAttribute("userName");
		if (bukenOptional.isPresent()) {
			Buken buken = bukenOptional.get();
			Okiniiri okiniiri = new Okiniiri();
			okiniiri.setUserName(userName);
			okiniiri.setPropertyId(buken.getPropertyId());
			okiniiri.setPropertyName(buken.getPropertyName());
			okiniiri.setAddress(buken.getAddress());
			okiniiri.setPropertyType(buken.getPropertyType());
			okiniiri.setPeriod(buken.getPeriod());
			okiniiri.setPropertyArea(buken.getPropertyArea());
			okiniiri.setPrice(buken.getPrice());
			okiniiri.setSyozokuCompanyId(buken.getSyozokuCompanyId());
			okiniiri.setStatus(buken.getStatus());
			okiniiri.setImagePath(buken.getImagePath());

			okiniiriRepository.save(okiniiri);
		}
	}

	// 審査され物件IDの保存
	public Long getMessagePropertyId(HttpSession session) {
		return (Long) session.getAttribute("propertyId");
	}

	// message新規(VIP申請)
	public void insertMessageVip(HttpSession session) {
		Message message = new Message();
		String userName = (String) session.getAttribute("userName");
		String messages = userName + "VIP申請の請求があります：";
		message.setMessages(messages);
		message.setStatus("未確認");
		messageRepository.save(message);
	}

	// message新規(審査申請)
	public void insertMessageShinsa(HttpSession session, String dropdown1Value, String dropdown2Value, Long bukenId) {
		Message message1 = new Message();
		message1.setMessages(bukenId + "号商品を審査の請求があります" + dropdown1Value);
		message1.setStatus("未確認");
		messageRepository.save(message1);

		Message message2 = new Message();
		message2.setMessages(bukenId + "号商品を審査の請求があります" + dropdown2Value);
		message2.setStatus("未確認");
		messageRepository.save(message2);
	}

	/**
	 * messageのStatus変更
	 */
	public void updateMessageStatus(MessageDto messageDto) {
		Long id = messageDto.getId();
		Message message = messageRepository.findById(id).orElse(null);

		if (message != null) {
			message.setStatus("確認済");
			messageRepository.save(message);
		}
	}

	/**
	 * userのStatus変更、VIP
	 */
	public void updateUserStatus(UserDto userDto) {
		Long id = userDto.getId();
		User user = userRepository.findById(id).orElse(null);

		if (user != null) {
			user.setStatus("VIP");
			userRepository.save(user);
		}
	}

	/**
	 * 審査のStatus変更
	 */

	public void updateShinsaStatusByBukenId(Long bukenId) {
		List<Shinsa> shinsaList = shinsaRepository.findAllByBukenId(bukenId);
		for (Shinsa shinsa : shinsaList) {
			shinsa.setStatus("一回審査完了（ここまだ作成終わってない）");
			shinsaRepository.save(shinsa);
		}
	}

	// ユーザ編集
	public void updateUser(UserDto userDto) {
		Long id = userDto.getId();
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			// 更新Buken对象的属性
			user.setUserName(userDto.getUserName());
			user.setPassword(userDto.getPassword());
			user.setStatus(userDto.getStatus());
			userRepository.save(user);
		} else {
			throw new NotFoundException("User not found with ID: " + id);
		}
	}

	// 物件更新（選ばれた物件のIDを取得）
	public BukenDto getBukenById(Long propertyId) {
		Optional<Buken> optionalBuken = bukenRepository.findById(propertyId);
		if (optionalBuken.isPresent()) {
			Buken buken = optionalBuken.get();
			return convertToDto(buken);
		} else {
			throw new NotFoundException("Buken not found with ID: " + propertyId);
		}
	}

	// ユーザ更新（選ばれたユーザのIDを取得）
	public UserDto getUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return convertToDtoUser(user);
		} else {
			throw new NotFoundException("User not found with ID: " + id);
		}
	}

}
