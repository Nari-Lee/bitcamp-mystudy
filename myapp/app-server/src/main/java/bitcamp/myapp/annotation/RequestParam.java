package bitcamp.myapp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * packageName    : bitcamp.myapp.annotation
 * fileName       : RequestParam
 * author         : narilee
 * date           : 24. 9. 19.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 19.        narilee       최초 생성
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME) // 이 어노테이션의 정보가 런타임까지 유지됨을 의미합니다.
public @interface RequestParam {
    String value();
}
