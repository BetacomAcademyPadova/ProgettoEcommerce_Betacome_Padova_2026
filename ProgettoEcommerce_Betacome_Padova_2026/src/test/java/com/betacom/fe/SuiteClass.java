package com.betacom.fe;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.betacom.fe.tipo.CategoriaTest;
import com.betacom.fe.tipo.SottoCategoriaTest;


@Suite
@SelectClasses({
	CategoriaTest.class,
	SottoCategoriaTest.class
})
public class SuiteClass {
}
