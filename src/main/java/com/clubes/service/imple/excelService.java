package com.clubes.service.imple;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clubes.model.clubes;
import com.clubes.repository.ClubesRepository;

@Service
public class excelService {

    @Autowired
    private ClubesRepository clubesRepository;

    public void save(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<clubes> clubesList = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // Saltar la fila de encabezado
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                clubes clubes = new clubes();

                // Verificar y asignar valores de las celdas
                clubes.setNombre(getStringCellValue(currentRow, 1));
                clubes.setLocalidad(getStringCellValue(currentRow, 2));
                clubes.setTelefono(getStringCellValue(currentRow, 3));  //  clubes.setTelefono(getIntegerCellValue(currentRow, 3));
                clubes.setDepartamento(getStringCellValue(currentRow, 4));
                clubes.setCelular(getStringCellValue(currentRow, 5));  // clubes.setCelular(getIntegerCellValue(currentRow, 5));
                clubes.setPaginaWeb(getStringCellValue(currentRow, 6));
                clubes.setCodigoPostal(getStringCellValue(currentRow, 7));
                clubes.setFechaFundacion(getDateCellValue(currentRow, 8));
                clubes.setPersoneriaJuridica(getStringCellValue(currentRow, 9));   // clubes.setPersoneriaJuridica(getIntegerCellValue(currentRow, 9));
                clubes.setNumeroLegajo(getStringCellValue(currentRow, 10));  //  clubes.setNumeroLegajo(getIntegerCellValue(currentRow, 10));
                clubes.setTipoLegal(getStringCellValue(currentRow, 11));
                clubes.setFechaPersoneriaJuridica(getDateCellValue(currentRow, 12));
                clubes.setNodo(getIntegerCellValue(currentRow, 13));
                clubes.setOpcionPersoneriaJuridica(getStringCellValue(currentRow, 14));
             
                clubes.setCalle(getStringCellValue(currentRow, 15));
                clubes.setNumeroDeCalle(getStringCellValue(currentRow, 16)); //  clubes.setNumeroDeCalle(getIntegerCellValue(currentRow, 16));
                clubes.setMail(getStringCellValue(currentRow, 17)); 

                // Imprimir valores para depuración
                System.out.println("Fila: " + rowNumber);
                System.out.println("Nombre: " + clubes.getNombre());
                System.out.println("Localidad: " + clubes.getLocalidad());
                System.out.println("telefono: " + clubes.getTelefono());
                System.out.println("departamento: " + clubes.getDepartamento());
                System.out.println("celular: " + clubes.getCelular());
                System.out.println("pagina web: " + clubes.getPaginaWeb());
                System.out.println("codigo postal: " + clubes.getCodigoPostal());
                System.out.println("fecha de fundacion: " + clubes.getFechaFundacion());
                System.out.println("personeria juridica: " + clubes.getPersoneriaJuridica());
                System.out.println("numero legajo: " + clubes.getNumeroLegajo());
                System.out.println("tipo legal: " + clubes.getTipoLegal());
                System.out.println("fecha de personeria juridica: " + clubes.getFechaPersoneriaJuridica());
                System.out.println("Nodo: " + clubes.getNodo());
                System.out.println("O. de personeria juridica: " + clubes.getOpcionPersoneriaJuridica());
                
                System.out.println("calle: " + clubes.getCalle());
                System.out.println("Numero de calle: " + clubes.getNumeroDeCalle());
                System.out.println("mail: " + clubes.getMail());
                
                // Verificar que los valores requeridos no sean nulos
  if (clubes.getNombre() == null ) {
                    throw new RuntimeException("Campos requeridos nulos en la fila: " + rowNumber);
                }
 
                clubesList.add(clubes);
                rowNumber++;
            }

            workbook.close();
            clubesRepository.saveAll(clubesList);
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar el archivo: " + e.getMessage());
        }
    }

    private String getStringCellValue(Row row, int cellIndex) {
        try {
            Cell cell = row.getCell(cellIndex);
            return cell != null ? cell.getStringCellValue() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private Integer getIntegerCellValue(Row row, int cellIndex) {
        try {
            Cell cell = row.getCell(cellIndex);
            return cell != null ? (int) cell.getNumericCellValue() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private Date getDateCellValue(Row row, int cellIndex) {
        try {
            Cell cell = row.getCell(cellIndex);
            if (cell != null) {
                String dateStr = cell.getStringCellValue();
                return new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
            }
        } catch (Exception e) {
            // Manejar la excepción si la conversión falla
        }
        return null;
    }
}
