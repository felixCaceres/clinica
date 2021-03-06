package framework;

import javax.swing.table.AbstractTableModel;
import java.util.*;
import java.beans.*;
import java.lang.reflect.Method;

/**
 * A table model for Swing that works easily with rows of JavaBeans.
 *
 * @author Christian Bauer
 * @author Marcelo Flores
 * 
 * Se agregaron metodos para permitir agregar o eliminar items de la lista
 */
public class EntityTableModel<T> extends AbstractTableModel {

    private Class<T> entityClass;
    private List<PropertyDescriptor> properties = new ArrayList<>();
    private List<T> rows;

    public EntityTableModel(Class<T> entityClass, Collection<T> rows) {
        this.entityClass = entityClass;
        this.rows = new ArrayList<>(rows);
    }

    public String getColumnName(int column) {
        return properties.get(column).getDisplayName();
    }

    public int getColumnCount() {
        return properties.size();
    }

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(int row, int column) {
        Object value = null;
        T entityInstance = rows.get(row);
        if (entityInstance != null) {
            PropertyDescriptor property = properties.get(column);
            Method readMethod = property.getReadMethod();
            try {
                value = readMethod.invoke(entityInstance);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return value;
    }

    public void addColumn(String displayName, String propertyName) {
        try {
            PropertyDescriptor property =
                    new PropertyDescriptor(propertyName, entityClass, propertyName, null);
            property.setDisplayName(displayName);
            properties.add(property);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void resetColumns() {
        properties = new ArrayList<PropertyDescriptor>();
    }

    public void setRow(int row, T entityInstance) {
        rows.remove(row);
        rows.add(row, entityInstance);
    }

    public void setRows(Collection<T> rows) {
        this.rows = new ArrayList<T>(rows);
    }
    
    /**
     * 
     * @param rowIndex
     * @return 
     */
    public T getItem(int rowIndex) throws IndexOutOfBoundsException{
        return rows.get(rowIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class t= properties.get(columnIndex).getPropertyType();
        return t;
    }

    public List<T> getRows() {
        return rows;
    }
    
    //Remueve un elemento
    public T removeItem(int rowIndex){
        return rows.remove(rowIndex);
    }
    
    
    
}