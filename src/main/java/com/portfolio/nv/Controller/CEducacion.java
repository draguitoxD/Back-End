package com.portfolio.nv.Controller;

import com.portfolio.nv.Dto.dtoEducacion;
import com.portfolio.nv.Entity.Educacion;
import com.portfolio.nv.Security.Controller.Mensaje;
import com.portfolio.nv.Service.SEducacion;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/educacion")
@CrossOrigin(origins = "https://front-end-porfolio-7915c.web.app")
public class CEducacion {

    @Autowired
    SEducacion sEducacion;

    @GetMapping("/lista")
    public ResponseEntity<List<Educacion>> list() {
        List<Educacion> list = sEducacion.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody dtoEducacion dtoedu) {
        if (StringUtils.isBlank(dtoedu.getNombreE())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        if (sEducacion.existsByNombreE(dtoedu.getNombreE())) {
            return new ResponseEntity(new Mensaje("Ese estudio ya existe"), HttpStatus.BAD_REQUEST);
        }

        Educacion educacion = new Educacion(dtoedu.getNombreE(), dtoedu.getDescripcionE());
        sEducacion.save(educacion);

        return new ResponseEntity(new Mensaje("Estudio agregado"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!sEducacion.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        }

        sEducacion.delete(id);

        return new ResponseEntity(new Mensaje("Estudio eliminado"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> udapte(@PathVariable("id") int id, @RequestBody dtoEducacion dtoedu) {
        if (!sEducacion.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.BAD_REQUEST);
        }

        if (sEducacion.existsByNombreE(dtoedu.getNombreE()) && sEducacion.getByNombreE(dtoedu.getNombreE()).get().getId() != id) {
            return new ResponseEntity(new Mensaje("Ese Estudio ya existe"), HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isBlank(dtoedu.getNombreE())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        Educacion educacion = sEducacion.getOne(id).get();
        educacion.setNombreE(dtoedu.getNombreE());
        educacion.setDescripcionE(dtoedu.getDescripcionE());

        sEducacion.save(educacion);

        return new ResponseEntity(new Mensaje("Estudio Actualizado"), HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Educacion> getById(@PathVariable("id") int id) {
        if (!sEducacion.existsById(id)) {
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        Educacion educacion = sEducacion.getOne(id).get();
        return new ResponseEntity(educacion, HttpStatus.OK);
    }
}
