CREATE DATABASE trousershop;
USE trousershop;

CREATE TABLE Users (
	id varchar(6) NOT NULL,
	userName varchar(50),
	password varchar(100),
	CONSTRAINT PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE Customers (
	id varchar(6) NOT NULL,
	name varchar(40),
	address varchar(60),
	mobile int(10),
	phone int(10),
	addedDate date,
	notes varchar(300),
	CONSTRAINT PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE Suppliers (
	id varchar(6) NOT NULL,
	name varchar(40),
	address varchar(60),
	mobile int(10),
	phone int(10),
	addedDate date,
	notes varchar(300),
	CONSTRAINT PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE Employees (
  	id varchar(6) NOT NULL,
  	name varchar(40),
	nic decimal(9,0),
	dob varchar(15),
	address varchar(60),
	salary decimal(10,2),
	mobile int(10),
	phone int(10),
	joinedDate date,
	notes varchar(300),
	CONSTRAINT PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE Items (
	code varchar(6) NOT NULL,
	description varchar(30),
	unitprice decimal(8,2),
	discount decimal(4,2),
	qtyOnHand int(10),
	addedDate date,
	notes varchar(300),
	CONSTRAINT PRIMARY KEY (code)
) ENGINE=InnoDB;

CREATE TABLE CustomerOrders (
	id varchar(6) NOT NULL,
	customerId varchar(6) NOT NULL,
	addedDate date,
	dueDate date,
	discount decimal(4,2),
	amount decimal(10,2),
	CONSTRAINT PRIMARY KEY (id),
	CONSTRAINT FOREIGN KEY(customerId) REFERENCES Customers(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE CustomerOrderDetails (
	orderId varchar(6) NOT NULL,
	itemCode varchar(6) NOT NULL,
	qty int(11),
	unitPrice decimal(10,2),
	discount decimal(4,2),
	CONSTRAINT PRIMARY KEY (orderId,itemCode),
	CONSTRAINT FOREIGN KEY(orderId) REFERENCES CustomerOrders (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FOREIGN KEY(itemCode) REFERENCES Items (code) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE PurchaseOrders (
	id varchar(6) NOT NULL,
	supplierId varchar(6) NOT NULL,
	addedDate date,
	dueDate date,
	amount decimal(10,2),
	CONSTRAINT PRIMARY KEY (id),
	CONSTRAINT FOREIGN KEY(supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE PurchaseOrderDetails (
	orderId varchar(6) NOT NULL,
	itemCode varchar(6) NOT NULL,
	qty int(11),
	unitPrice decimal(10,2),
	CONSTRAINT PRIMARY KEY (orderId,itemCode),
	CONSTRAINT FOREIGN KEY(orderId) REFERENCES PurchaseOrders (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FOREIGN KEY(itemCode) REFERENCES Items (code) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE CustomerPayments (
	paymentId varchar(6) NOT NULL,
	orderId varchar(6) NOT NULL,
	date date,
	payment decimal(10,2),
	paidAmount decimal(10,2),
	invoiceNo varchar(10),
	method varchar(10),
	chequeId VARCHAR(6),
	CONSTRAINT PRIMARY KEY (paymentId,orderId),
	CONSTRAINT FOREIGN KEY(orderId) REFERENCES CustomerOrders (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE SupplierPayments (
	paymentId varchar(6) NOT NULL,
	orderId varchar(6) NOT NULL,
	date date,
	payment decimal(10,2),
	paidAmount decimal(10,2),
	invoiceNo varchar(10),
	method varchar(10),
	chequeId VARCHAR(6),
	CONSTRAINT PRIMARY KEY (paymentId,orderId),
	CONSTRAINT FOREIGN KEY(orderId) REFERENCES PurchaseOrders (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE RecievedCheques (
	id varchar(6) NOT NULL,
	orderId varchar(6) NOT NULL,
	number varchar(30),
	bank varchar(30),
	issuedDate date,
	realDate date,
	amount decimal(10,2),
	CONSTRAINT PRIMARY KEY (id,orderId),
	CONSTRAINT FOREIGN KEY(orderId) REFERENCES CustomerOrders (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IssuedCheques (
	id varchar(6) NOT NULL,
	orderId varchar(6) NOT NULL,
	number varchar(30),
	bank varchar(30),
	issuedDate date,
	realDate date,
	amount decimal(10,2),
	CONSTRAINT PRIMARY KEY (id,orderId),
	CONSTRAINT FOREIGN KEY(orderId) REFERENCES PurchaseOrders (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE RecievedChequeStatus (
	id varchar(6) NOT NULL,
	chequeId varchar(6) NOT NULL,
	status varchar(10),
	CONSTRAINT PRIMARY KEY (id),
	CONSTRAINT FOREIGN KEY(chequeId) REFERENCES RecievedCheques(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IssuedChequeStatus (
	id varchar(6) NOT NULL,
	chequeId varchar(6) NOT NULL,
	status varchar(10),
	CONSTRAINT PRIMARY KEY (id),
	CONSTRAINT FOREIGN KEY(chequeId) REFERENCES IssuedCheques(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE PurchOrderReports (
	id varchar(6) NOT NULL,
	printedDate date,
	orderId varchar(6) NOT NULL,
	CONSTRAINT PRIMARY KEY (id),
	CONSTRAINT FOREIGN KEY(orderId) REFERENCES PurchaseOrders (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;


INSERT INTO Users VALUES ('US0001','root',(SELECT PASSWORD('asdf')));

INSERT INTO Customers VALUES ('CS0001','Akon','LA',717600072,912222222,'2013-08-06','Note01');
INSERT INTO Customers VALUES ('CS0002','Lil Wayne','LA',717600072,912222222,'2013-08-06','Note02');
INSERT INTO Customers VALUES ('CS0003','50 cent','LA',717600072,912222222,'2013-08-06','Note03');
INSERT INTO Customers VALUES ('CS0004','Eminem','LA',717600072,912222222,'2013-08-06','Note04');
INSERT INTO Customers VALUES ('CS0005','Chaminda','Galle',716546514,0,'2013-09-14','Note05');

INSERT INTO Suppliers VALUES ('SP0001','Sunil','Gampaha',717600072,912222222,'2013-08-06','Note01');
INSERT INTO Suppliers VALUES ('SP0002','Sanath','Mathale',717600072,912222222,'2013-08-06','Note02');
INSERT INTO Suppliers VALUES ('SP0003','Amara','Kaluthara',717600072,912222222,'2013-08-06','Note03');
INSERT INTO Suppliers VALUES ('SP0004','Damith','Hungama',717600072,912222222,'2013-08-06','Note04');
INSERT INTO Suppliers VALUES ('SP0005','Ajith','Mathara',716546514,0,'2013-09-14','Note05');

INSERT INTO Employees VALUES ('EM0001','Chanaka',922901120,'1992-10-16','Mapalagama, Galle',30000.00,717600072,915710431,'2013-08-17','Note01');
INSERT INTO Employees VALUES ('EM0002','Isuru',921601120,'1992-06-14','Ganegoda, Galle',35000.00,716576062,912296998,'2013-08-17','Note02');
INSERT INTO Employees VALUES ('EM0003','Rukshan',921551120,'1992-05-14','Ambalangoda, Galle',45000.00,757589654,912296999,'2013-05-17','Note03');
INSERT INTO Employees VALUES ('EM0004','Buddhi',922701120,'1992-10-01','Imaduwa, Galle',35000.00,717345672,912296990,'2013-08-18','Note04');

INSERT INTO Items VALUES ('IT0001','Male Casual Trousers 32inch',1000.00,5.00,46,'2013-08-25','Note01');
INSERT INTO Items VALUES ('IT0002','Male Cargo Pants 34inch',2000.00,7.00,48,'2013-08-25','Note02');
INSERT INTO Items VALUES ('IT0003','Male sportswear Pants medium',1000.00,8.00,47,'2013-08-25','cotton');
INSERT INTO Items VALUES ('IT0004','Mens Casual Jeans 30inch',1500.00,5.00,50,'2013-08-25','cotton');

INSERT INTO CustomerOrders VALUES ('CO0001','CS0001','2013-09-13','2013-09-27',10.00,25137.00);
INSERT INTO CustomerOrders VALUES ('CO0002','CS0001','2013-09-13','2013-09-27',10.00,22491.00);
INSERT INTO CustomerOrders VALUES ('CO0003','CS0002','2013-09-20','2013-09-25',10.00,23814.00);
INSERT INTO CustomerOrders VALUES ('CO0004','CS0002','2013-09-19','2013-09-26',10.00,1710.00);
INSERT INTO CustomerOrders VALUES ('CO0005','CS0001','2013-09-20','2013-09-27',10.00,855.00);
INSERT INTO CustomerOrders VALUES ('CO0006','CS0003','2013-09-14','2013-09-28',10.00,0.00);
INSERT INTO CustomerOrders VALUES ('CO0007','CS0004','2013-09-17','2013-09-24',10.00,0.00);
INSERT INTO CustomerOrders VALUES ('CO0008','CS0005','2013-09-20','2013-09-22',5.00,10858.50);

INSERT INTO CustomerOrderDetails VALUES ('CO0001','IT0001',3,1000.00,2.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0001','IT0002',5,2000.00,2.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0001','IT0003',8,1000.00,2.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0001','IT0004',2,1500.00,2.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0002','IT0001',4,1000.00,2.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0002','IT0002',4,2000.00,2.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0002','IT0003',3,1000.00,2.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0002','IT0004',7,1500.00,2.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0003','IT0001',3,1000.00,2.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0003','IT0002',9,2000.00,2.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0003','IT0003',6,1000.00,2.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0004','IT0001',2,1000.00,5.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0005','IT0001',1,1000.00,5.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0008','IT0001',2,1000.00,5.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0008','IT0002',2,2000.00,2.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0008','IT0003',3,1000.00,8.00);
INSERT INTO CustomerOrderDetails VALUES ('CO0008','IT0004',2,1500.00,5.00);

INSERT INTO PurchaseOrders VALUES ('PO0001','SP0001','2013-09-13','2013-09-27',25137.00);
INSERT INTO PurchaseOrders VALUES ('PO0002','SP0001','2013-09-13','2013-09-27',22491.00);
INSERT INTO PurchaseOrders VALUES ('PO0003','SP0002','2013-09-20','2013-09-25',23814.00);
INSERT INTO PurchaseOrders VALUES ('PO0004','SP0002','2013-09-19','2013-09-26',1710.00);
INSERT INTO PurchaseOrders VALUES ('PO0005','SP0001','2013-09-20','2013-09-27',855.00);
INSERT INTO PurchaseOrders VALUES ('PO0006','SP0003','2013-09-14','2013-09-28',0.00);
INSERT INTO PurchaseOrders VALUES ('PO0007','SP0004','2013-09-17','2013-09-24',0.00);
INSERT INTO PurchaseOrders VALUES ('PO0008','SP0005','2013-09-20','2013-09-22',10858.50);

INSERT INTO PurchaseOrderDetails VALUES ('PO0001','IT0001',3,1000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0001','IT0002',5,2000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0001','IT0003',8,1000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0001','IT0004',2,1500.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0002','IT0001',4,1000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0002','IT0002',4,2000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0002','IT0003',3,1000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0002','IT0004',7,1500.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0003','IT0001',3,1000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0003','IT0002',9,2000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0003','IT0003',6,1000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0004','IT0001',2,1000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0005','IT0001',1,1000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0008','IT0001',2,1000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0008','IT0002',2,2000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0008','IT0003',3,1000.00);
INSERT INTO PurchaseOrderDetails VALUES ('PO0008','IT0004',2,1500.00);

INSERT INTO CustomerPayments VALUES ('CP0001','CO0001','2013-09-09',1200.00,1200.00,'V001','cheque','Q001');
INSERT INTO CustomerPayments VALUES ('CP0002','CO0001','2013-09-11',3800.00,5000.00,'V002','cheque','Q002');
INSERT INTO CustomerPayments VALUES ('CP0003','CO0002','2013-09-11',1200.00,1200.00,'V003','cash','');
INSERT INTO CustomerPayments VALUES ('CP0004','CO0002','2013-09-11',1200.00,2400.00,'V004','cash','');
INSERT INTO CustomerPayments VALUES ('CP0005','CO0002','2013-09-19',10000.00,12400.00,null,'cheque','Q003');

INSERT INTO SupplierPayments VALUES ('SP0001','PO0001','2013-09-09',1200.00,1200.00,'V001','cheque','Q001');
INSERT INTO SupplierPayments VALUES ('SP0002','PO0001','2013-09-11',3800.00,5000.00,'V002','cheque','Q002');
INSERT INTO SupplierPayments VALUES ('SP0003','PO0002','2013-09-11',1200.00,1200.00,'V003','cash','');
INSERT INTO SupplierPayments VALUES ('SP0004','PO0002','2013-09-11',1200.00,2400.00,'V004','cash','');
INSERT INTO SupplierPayments VALUES ('SP0005','PO0002','2013-09-19',10000.00,12400.00,null,'cheque','Q003');

INSERT INTO RecievedCheques VALUES ('QR0001','CO0001','cheque001','BOC','2013-09-11','2013-09-18',5000.00);
INSERT INTO RecievedCheques VALUES ('QR0002','CO0001','cheque002','BOC','2013-09-11','2013-09-18',5000.00);
INSERT INTO RecievedCheques VALUES ('QR0003','CO0002','cheque003','BOC','2013-09-11','2013-09-18',5000.00);
INSERT INTO RecievedCheques VALUES ('QR0004','CO0003','cheque004','BOC','2013-09-11','2013-09-18',5000.00);
INSERT INTO RecievedCheques VALUES ('QR0005','CO0008','cheque005','Peoples','2013-09-19','2013-09-26',3000.00);

INSERT INTO IssuedCheques VALUES ('QI0001','PO0001','cheque001','BOC','2013-09-11','2013-09-18',5000.00);
INSERT INTO IssuedCheques VALUES ('QI0002','PO0001','cheque002','BOC','2013-09-11','2013-09-18',5000.00);
INSERT INTO IssuedCheques VALUES ('QI0003','PO0002','cheque003','BOC','2013-09-11','2013-09-18',5000.00);
INSERT INTO IssuedCheques VALUES ('QI0004','PO0003','cheque004','BOC','2013-09-11','2013-09-18',5000.00);
INSERT INTO IssuedCheques VALUES ('QI0005','PO0008','cheque005','Peoples','2013-09-19','2013-09-26',3000.00);

INSERT INTO RecievedChequeStatus VALUES ('SR0001','QR0001','done');
INSERT INTO RecievedChequeStatus VALUES ('SR0002','QR0002','done');
INSERT INTO RecievedChequeStatus VALUES ('SR0003','QR0003','pending');
INSERT INTO RecievedChequeStatus VALUES ('SR0004','QR0004','pending');
INSERT INTO RecievedChequeStatus VALUES ('SR0005','QR0005','pending');

INSERT INTO IssuedChequeStatus VALUES ('SI0001','QI0001','done');
INSERT INTO IssuedChequeStatus VALUES ('SI0002','QI0002','done');
INSERT INTO IssuedChequeStatus VALUES ('SI0003','QI0003','pending');
INSERT INTO IssuedChequeStatus VALUES ('SI0004','QI0004','pending');
INSERT INTO IssuedChequeStatus VALUES ('SI0005','QI0005','pending');

