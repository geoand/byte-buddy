package net.bytebuddy.implementation.bytecode.assign;


import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackSize;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * A stack manipulation for a type down casting. Such castings are not implicit but must be performed explicitly.
 */
public class TypeCasting implements StackManipulation {

    public static StackManipulation to(TypeDescription typeDescription) {
        if (typeDescription.isPrimitive()) {
            throw new IllegalArgumentException("Cannot cast to primitive type " + typeDescription);
        }
        return new TypeCasting(typeDescription);
    }

    private final TypeDescription typeDescription;

    protected TypeCasting(TypeDescription typeDescription) {
        this.typeDescription = typeDescription;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context implementationContext) {
        methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, typeDescription.getInternalName());
        return StackSize.ZERO.toIncreasingSize();
    }

    @Override
    public boolean equals(Object other) {
        return this == other || !(other == null || getClass() != other.getClass())
                && typeDescription.equals(((TypeCasting) other).typeDescription);
    }

    @Override
    public int hashCode() {
        return typeDescription.hashCode();
    }

    @Override
    public String toString() {
        return "TypeCasting{typeDescription='" + typeDescription + '\'' + '}';
    }
}
