package Admin.LearnTogether.Converter;

/*
  Created by Luvbert
*/

public interface IConverter<D, E> {
    D toDTO(E entity);
    E toEntity(D dto);
}
