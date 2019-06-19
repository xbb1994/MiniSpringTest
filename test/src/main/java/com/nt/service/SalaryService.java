package com.nt.service;/**
 * @author ：xubb
 * @date ：Created in 2019/6/19 17:09
 * @description：
 */

import com.nt.beans.Bean;

/**
 * @author     ：xubb
 * @date       ：Created in 2019/6/19 17:09
 * @description：
 */
@Bean
public class SalaryService {
    public int getSalary(int experience){
        return experience*10000;
    }

}
