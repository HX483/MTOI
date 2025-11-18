import SvgIcon from '@/components/SvgIcon'

// 注册所有SVG图标
const req = require.context('./svg', false, /\.svg$/)\nconst requireAll = requireContext => requireContext.keys().map(requireContext)
requireAll(req)

export default {
  install(app) {
    app.component('svg-icon', SvgIcon)
  }
}
