import type { Construct } from "proto/construct";
import type { Tableable } from "./tableable";
import type { Query } from "proto/query";
import { post } from "../api";
import { ListConstructRequest, ListConstructResponse } from "../../../proto/construct_api";

export function tableConstruct(): Tableable<Construct> {
  return {
    name: 'construct',
    list: async (query: Query) => {
      const list_request = ListConstructRequest.create({query});
      const response = await post<ListConstructResponse>('construct/list', ListConstructRequest, list_request, ListConstructResponse);
      if (!response.success) {
        return {
          success: false,
          error_message: response.error_message,
        };
      }
      if (response.data?.errorMessage) {
        return {
          success: false,
          error_message: response.data?.errorMessage,
        };
      }
      return {
        success: true,
        data: response.data?.constructs,
      };
    },
    cols: ["Name", "Description"],
    row: (construct: Construct): string[] => {
      return [
        construct.name ?? '',
        construct.description ?? '',
      ];
    },
  };
}
