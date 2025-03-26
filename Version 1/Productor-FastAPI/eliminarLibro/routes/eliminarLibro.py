from fastapi import APIRouter, HTTPException
from schemas.dto import LibroDeleteDTO
from config.rabbitConf import send_mensage, COMMON_ROUTING_KEY ,SOCORRO_ROUTING_KEY , MALAGA_ROUTING_KEY, BARBOSA_ROUTING_KEY

router = APIRouter()

@router.delete("/eliminar-libro/{destino}")
async def eliminar_libro(destino: str, libro_delete: LibroDeleteDTO):
    # 'destino' puede ser "socorro", "malaga", "barbosa" o "common"
    destinolower = destino.lower()
    if destinolower == "common":
        key = COMMON_ROUTING_KEY
    elif destinolower == "socorro":
        key = SOCORRO_ROUTING_KEY
    elif destinolower == "malaga":
        key = MALAGA_ROUTING_KEY
    elif destinolower == "barbosa":
        key = BARBOSA_ROUTING_KEY
    else:
        raise HTTPException(status_code=400, detail="Destino no válido")
    
    return await send_mensage(libro_delete, key)

