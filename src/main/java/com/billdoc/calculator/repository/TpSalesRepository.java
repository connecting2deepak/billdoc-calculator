/**
 * 
 */
package com.billdoc.calculator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.billdoc.calculator.mongodb.model.TpSales;

/**
 * @author Deepak M S
 *
 */

@Repository
public interface TpSalesRepository extends MongoRepository<TpSales, String> {

    TpSales findOneByDocNbrAndCompanyNbr(final Integer docNbr, final String companyNbr);

}
