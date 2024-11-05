package com.clubes.service.imple;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clubes.model.ClubDTO;
import com.clubes.model.clubes;

import com.clubes.repository.ClubesRepository;

import com.clubes.service.ClubesService;

@Service
public class ClubesServiceImpl implements ClubesService {

    @Autowired
    ClubesRepository repo;



    @Override
    public List<clubes> listar() {
        return repo.findAll();
    }

    @Override
    public clubes registrar(clubes clubes) {
        return repo.save(clubes);
    }

    @Override
    public clubes actualizar(clubes clubes) {
        return repo.save(clubes);
    }

    @Override
    public void eliminar(Integer codigo) {
        repo.deleteById(codigo);
    }

    @Override
    public clubes ListarPorId(Integer codigo) {
        return repo.findById(codigo).orElse(null);
    }

    @Override
    public Page<clubes> listPageable(Pageable pageable) {
        return repo.findAll(pageable);
    }
    
    public String formatFechaFundacion(Date fechaFundacion) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(fechaFundacion);
    }

    @Override
    public clubes save(clubes club) {
        return repo.save(club);  // Usamos el repositorio para guardar el club
    }


    // Implementación del nuevo método
    @Override
    public List<ClubDTO> listarNombresYFechas() {
        List<ClubDTO> result = new ArrayList<>();
        List<clubes> clubesList = repo.findAll();
    
        for (clubes club : clubesList) {
            ClubDTO dto = new ClubDTO();
            dto.setNombre(club.getNombre());
            dto.setFechaFundacion(formatFechaFundacion(club.getFechaFundacion())); // Aquí se formatea la fecha si es necesario
            result.add(dto);
        }
    
        return result;
    }

    public void saveExcelData(MultipartFile file) throws Exception {
        List<clubes> clubesList = new ArrayList<>();
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // skip the header row
                    continue;
                }
                clubes clubes = new clubes();
                clubes.setNombre(row.getCell(5).getStringCellValue());
                clubes.setLocalidad(row.getCell(4).getStringCellValue());
                clubes.setTelefono(row.getCell(12).getStringCellValue()); //clubes.setTelefono((int) row.getCell(12).getNumericCellValue());
                clubes.setCelular(row.getCell(17).getStringCellValue());   //  clubes.setCelular((int) row.getCell(17).getNumericCellValue());
                clubes.setPaginaWeb(row.getCell(10).getStringCellValue());
                clubes.setCodigoPostal(row.getCell(9).getStringCellValue());
                clubes.setFechaFundacion(row.getCell(18).getDateCellValue());
                clubes.setPersoneriaJuridica(row.getCell(19).getStringCellValue()); //clubes.setPersoneriaJuridica((int)row.getCell(19).getNumericCellValue());
                clubes.setNumeroLegajo(row.getCell(20).getStringCellValue());  // clubes.setNumeroLegajo((int)row.getCell(20).getNumericCellValue());
                clubes.setTipoLegal(row.getCell(21).getStringCellValue());
                clubes.setDepartamento(row.getCell(3).getStringCellValue());
                clubes.setFechaPersoneriaJuridica(row.getCell(22).getDateCellValue());
                clubes.setNodo((int) row.getCell(2).getNumericCellValue());
                clubes.setOpcionPersoneriaJuridica(row.getCell(23).getStringCellValue());
                
                clubes.setCalle(row.getCell(24).getStringCellValue());
                clubes.setNumeroDeCalle(row.getCell(25).getStringCellValue()); //clubes.setNumeroDeCalle((int) row.getCell(25).getNumericCellValue());
                clubes.setMail(row.getCell(26).getStringCellValue());

                clubesList.add(clubes);
            }
            repo.saveAll(clubesList);
        }
    }
}

