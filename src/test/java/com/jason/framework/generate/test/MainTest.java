package com.jason.framework.generate.test;

import com.jason.framework.MyBatisTestBase;

public class MainTest extends MyBatisTestBase {
/*
	private final static Logger logger = LoggerFactory.getLogger(MainTest.class);
	
	@Autowired
	private ContactService contactService;
	
	private Contact entity ;
	
	@Before
	public void testSave(){
		Date now = new Date();
		
		entity = new Contact();
		entity.setCreatedAt(now);
		entity.setMobile(13750357236L);
		entity.setName("Jason");
		entity.setUpdatedAt(now);
		contactService.save(entity);
	}
	@Test
	public void testGet(){
		long id = entity.getId();
		Contact contact = contactService.get(id);
		logger.info(contact.getName());
	}
	
	@Test
	public void testQueryPage(){
		Page<Contact> page = new Page<Contact>();
		page = contactService.queryPage(page);
		System.out.println("总数："+page.getTotalCount());
		List<Contact> list = page.getResult();
		for (Contact t:list) {
			System.out.println(t.getId()+" "+t.getName()+" "+t.getMobile());
		}
	}
	@Test
	public void testUpdate(){
		entity.setName("Tan-Jason");
		contactService.update(entity);
		Contact t = contactService.get(entity.getId());
		Assert.assertEquals("Tan-Jason", t.getName());
	}
	*/
	
}
