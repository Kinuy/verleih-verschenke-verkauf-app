import {ItemCategory} from "./ItemCategory.ts";
import {ItemStatus} from "./ItemStatus.ts";

export type ItemDto = {
    name: string,
    img: string,
    description: string,
    category: ItemCategory,
    status: ItemStatus,
    geocode: [number,number],
    owner: string
}