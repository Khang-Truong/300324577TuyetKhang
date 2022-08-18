package com.example.finalcsis3275;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class loantable {
    @Id
    @Size(max = 8)
    private String clientno;
    @Size(max = 20)
    private String clientname;
    private double loananmount;
//    @Size(max = 11)
    private Integer years;
    @Size(max = 20)
    private String loantype;
}
